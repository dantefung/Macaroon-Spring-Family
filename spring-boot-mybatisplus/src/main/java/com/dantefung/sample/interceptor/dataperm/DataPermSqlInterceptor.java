package com.dantefung.sample.interceptor.dataperm;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RegExUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基于用户授权中心再一次于做sql统一拦截拼接的自定义MyBatis 拦截器
 */
@Component
@Intercepts({
        @Signature(type = Executor.class, method = "query",
                args = {
                        MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class
                })
})
@Slf4j
public class DataPermSqlInterceptor implements Interceptor {
    /**args的序号*/
    private static final int MAPPED_STATEMENT_INDEX = 0;
    private static final int PARAMETER_INDEX = 1;

    private static final String FUNC_PERM_REG ="\\{@(.*?)\\}";
    @Autowired
    private DataPermProcessor dataPermProcessor;

    public DataPermSqlInterceptor(DataPermProcessor dataPermProcessor) {
        this.dataPermProcessor = dataPermProcessor;
    }
    /**
     * 对拦截的sql进行具体的操作
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("执行intercept方法：{}", invocation.toString());
        //获取sql
        MappedStatement originalMappedStatement = (MappedStatement) invocation.getArgs()[MAPPED_STATEMENT_INDEX];
        Object paramter = invocation.getArgs()[PARAMETER_INDEX];
        BoundSql originalBoundSql = originalMappedStatement.getBoundSql(paramter);
        String originalSql = originalBoundSql.getSql();
        log.info("originalSql:"+originalSql);
        //无法获取originalSql将无法继续执行，如果没有捕获异常的情况下
        Assert.notNull(originalSql, "Cannot get the originalSql String!");
        // sql语句类型 select、delete、insert、update
        String sqlCommandType = originalMappedStatement.getSqlCommandType().toString();
        //仅拦截 select 查询
        if(!sqlCommandType.equals(SqlCommandType.SELECT.toString())){
            return invocation.proceed();
        }
        //Mapper中没有{@自定义标识}，则直接跳过，按原来SQL执行
        List<String> dataPermList =  getSubStringByRegx(originalSql,FUNC_PERM_REG);
        if(!dataPermList.isEmpty()){
            log.info("dataPermList List is >>>: {}",dataPermList);
            //查询出对应标识码的数据源，Map<String, String> dataPermSqlClauseParsedMap
            //根据当前登录用户获取其所拥有的组织数据权限
            Map<String, String> dataPermSqlClauseParsedMap = dataPermProcessor.process(dataPermList);
            //使用数据源替换原sql中的标识码得到新sql
            String replacedSql = replaceParsedDataPerm(originalSql,dataPermSqlClauseParsedMap);
			if (log.isInfoEnabled()) {
				log.info(replacedSql);
			}
            handleSqlWithDataPerm(invocation,replacedSql,originalMappedStatement,originalBoundSql);

        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        log.info("不重写");
    }

    /**
     * 获取sql中所有的  {@自定义标识}
     * @param targetString
     * @param regex
     * @return
     */
    public List<String> getSubStringByRegx(String targetString, String regex) {
        List<String> subStrList = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(targetString);
        while (matcher.find()) {
            //得到除@以外的字符串
            subStrList.add(matcher.group(1));
        }
        return subStrList;
    }

    /**
     * 替换在原SQL中所加入的功能权限标签为解析后的SQL clause
     *
     * @param originalSql
     * @param dataPermSqlClauseParsedMap
     * @return
     */
    private String replaceParsedDataPerm(String originalSql, Map<String, String> dataPermSqlClauseParsedMap) {
        Assert.notNull(dataPermSqlClauseParsedMap, "funcPermSqlClauseParsedMap cannot be null!");
        Iterator<Map.Entry<String, String>> iterator = dataPermSqlClauseParsedMap.entrySet().iterator();
        String replacedResult = originalSql;
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            replacedResult = RegExUtils.replaceAll(replacedResult, "\\{@" + entry.getKey() + "\\}", entry.getValue());
        }
        return replacedResult;
    }

    private void handleSqlWithDataPerm(Invocation invocation,String newSql,MappedStatement originalMappedStatement,BoundSql originalBoundSql) {
        // 重新new一个查询语句对像
        BoundSql newBoundSql = new BoundSql(originalMappedStatement.getConfiguration(),newSql,
                originalBoundSql.getParameterMappings(),originalBoundSql.getParameterObject());
        for (ParameterMapping mapping : originalBoundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (originalBoundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, originalBoundSql.getAdditionalParameter(prop));
            }
        }
        // 把新的查询放到statement里
        MappedStatement newMs = buildNewMappedStatement(originalMappedStatement, new BoundSqlSqlSource(newBoundSql));
        invocation.getArgs()[MAPPED_STATEMENT_INDEX] = newMs;
        log.info("newMappedStatement:{}",originalMappedStatement);
    }

    private MappedStatement buildNewMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            builder.keyProperty(ms.getKeyProperties()[0]);
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }
    /**
     * 定义一个内部辅助类，作用是包装 SQL
     */
    class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;
        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }
        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }

    }
}
