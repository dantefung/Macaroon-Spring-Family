/**
 * Copyright (C), 2018-2019, 独立开发者DanteFung
 * FileName: SqlSessionTemplateUtils
 * Author:   admin
 * Date:     2019-10-29 00:31
 * Description: 根据不同数据源手工创建SqlSessionTemplate
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dantefung.sample.core.sql;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author Dante Fung

 * @create 2019-10-29 00:31

 * @desc 根据不同数据源手工创建SqlSessionTemplate

 * @since 1.0.0
 **/
public class SqlSessionTemplateUtils {

    private SqlSessionTemplateUtils(){

    }

    public static SqlSessionTemplate createSqlSessionTemplate(DataSource dataSource) {
    	// FactoryBean 也是接口，为 IOC 容器中 Bean 的实现提供了更加灵活的方式，
		// FactoryBean 在 IOC 容器的基础上给 Bean 的实现加上了一个简单工厂模式和装饰模式
		// (如果想了解装饰模式参考：修饰者模式 (装饰者模式，Decoration)
		// 我们可以在 getObject() 方法中灵活配置。其实在 Spring 源码中有很多 FactoryBean 的实现类.
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactory sqlSessionFactory = null;
        // SqlSession session = null;
        SqlSessionTemplate sqlSessionTemplate = null;
        try {
            factoryBean.setMapperLocations(resolver.getResources("classpath*:/com/dantefung/**/mapper/*.xml"));
            factoryBean.setTypeAliasesPackage("com.dantefung.sample.*.entity");
            sqlSessionFactory = factoryBean.getObject();
            sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
            // session = sqlSessionFactory.openSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlSessionTemplate;
    }
}
