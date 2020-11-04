package com.dantefung.sample;

import com.alibaba.druid.pool.DruidDataSource;
import com.dantefung.sample.core.sql.SqlMapper;
import com.dantefung.sample.core.sql.SqlSessionTemplateUtils;
import com.dantefung.sample.datasource.config.DynamicDataSourceFactory;
import com.dantefung.sample.datasource.properties.DataSourceProperties;
import com.dantefung.sample.teacher.domain.User;
import com.dantefung.sample.teacher.domain.UserExample;
import com.dantefung.sample.teacher.mapper.GenericMapper;
import com.dantefung.sample.teacher.mapper.UserMapper;
import lombok.Data;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlSessionTemplateUtilsTest {


	@Test
	public void testSqlite() {
		String dbType = "sqlite";
		String driverClassName = "org.sqlite.JDBC";
		String url = "jdbc:sqlite:data/martini.db?date_string_format=yyyy-MM-dd HH:mm:ss";
		String userName = "";
		String password = "";
		DataSourceProperties dataSourceProperties = new DataSourceProperties();
		dataSourceProperties.setDbType(dbType);
		dataSourceProperties.setDriverClassName(driverClassName);
		dataSourceProperties.setUrl(url);
		dataSourceProperties.setUsername(userName);
		dataSourceProperties.setPassword(password);

		DruidDataSource druidDataSource = DynamicDataSourceFactory.buildDruidDataSource(dataSourceProperties);
		SqlSessionTemplate sqlSessionTemplate = SqlSessionTemplateUtils.createSqlSessionTemplate(druidDataSource);
		SqlMapper sqlMapper = new SqlMapper(sqlSessionTemplate.getSqlSessionFactory().openSession());
		String sql = "PRAGMA table_info([martini_kernel_dbinfo])";
		List<Map> maps = sqlMapper.selectList(sql, Map.class);
		System.out.println(maps);
	}

    @Test
    public void test() {
        String dbType = "mysql";
        String driverClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/teacher?useUnicode=true&characterEncoding=UTF-8&useSSL=false&&serverTimezone=UTC";
        String userName = "root";
        String password = "root";
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setDbType(dbType);
        dataSourceProperties.setDriverClassName(driverClassName);
        dataSourceProperties.setUrl(url);
        dataSourceProperties.setUsername(userName);
        dataSourceProperties.setPassword(password);

        DruidDataSource druidDataSource = DynamicDataSourceFactory.buildDruidDataSource(dataSourceProperties);
        SqlSessionTemplate sqlSessionTemplate = SqlSessionTemplateUtils.createSqlSessionTemplate(druidDataSource);
        String statement = "com.dantefung.sample.teacher.mapper.UserMapper.selectUserDailyReport";
        List<Object> objects = sqlSessionTemplate.selectList(statement);
        System.out.println(objects);
		String queryTableSqlId = "com.dantefung.sample.teacher.mapper.UserMapper.selectUserDailyReport";
		Map<String, Map<String, String>> resultMap = sqlSessionTemplate.selectMap(queryTableSqlId, "", "key");
		System.out.println(new ArrayList<>(resultMap.values()).get(0));
		BoundSql boundSql = sqlSessionTemplate.getConfiguration().getMappedStatement(queryTableSqlId)
				.getBoundSql("");
		System.out.println(boundSql.getParameterMappings());
		System.out.println(boundSql.getParameterObject());

		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		UserExample userExample = new UserExample();
		UserExample.Criteria criteria = userExample.createCriteria();
		criteria.andIdEqualTo(1L);
		userExample.setDistinct(Boolean.TRUE);
		userExample.setOrderByClause("create_time DESC");
		List<User> users = mapper.selectByExample(userExample);
		System.out.println(users);

		System.out.println("=================================使用自定义GenericMapper执行原生SQL======================================");

		GenericMapper genericMapper = sqlSessionTemplate.getMapper(GenericMapper.class);
		int count = genericMapper.selectCount("select count(1) from (\n" + "\n"
				+ "select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables\n"
				+ "\t\twhere table_schema = (select database())\n" + "\t\t\n" + "\t\torder by create_time desc\n"
				+ "\t\t \n" + "\t\t) t");
		System.out.println(count);

		List<Map> resultList = genericMapper.select("select t.* from (\n" + "\n"
				+ "select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables\n"
				+ "\t\twhere table_schema = (select database())\n" + "\t\t\n" + "\t\torder by create_time desc\n"
				+ "\t\t \n" + "\t\t) t");
		System.out.println(resultList);


		System.out.println("================================使用Mybatis执行原生SQL===================================");
		String sql = "select t.* from (\n" + "\n"
				+ "select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables\n"
				+ "\t\twhere table_schema = (select database())\n" + "\t\t\n" + "\t\torder by create_time desc\n"
				+ "\t\t \n" + "\t\t) t";
		Configuration configuration = sqlSession.getConfiguration();

		StringBuilder msIdBuilder = new StringBuilder(SqlCommandType.SELECT.toString());
		String msId = msIdBuilder.append(".").append(sql.hashCode()).toString();
		System.out.println("msId: " + msId);

		// 给定一个查询唯一ID标识，并不校验是否是完整语句
		configuration.hasStatement(msId, false);

		// 设置要执行的静态SQL
		StaticSqlSource sqlSource = new StaticSqlSource(configuration, sql);

		// 设置返回值映射
		List<ResultMap> resultMapList = new ArrayList<>();
		resultMapList.add(new ResultMap.Builder(configuration, "defaultResultMap",  Map.class, new ArrayList<>(0)).build());
		//
		MappedStatement ms = new MappedStatement.Builder(configuration, msId, sqlSource, SqlCommandType.SELECT)
				.resultMaps(resultMapList)
				.build();
		// 缓存
		configuration.addMappedStatement(ms);
		// 执行查询
		List<Object> list = sqlSession.selectList(msId);
		System.out.println(list);

		// 释放资源
		sqlSession.close();
	}

}
