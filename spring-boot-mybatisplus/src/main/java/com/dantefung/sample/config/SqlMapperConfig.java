package com.dantefung.sample.config;

import com.dantefung.sample.core.sql.SqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @Author hwc
 */
@Configuration
public class SqlMapperConfig {

    /**
     * 使用原型模式，不能使用单例模式
     * @param sqlSession
     * @return
     */
    @Bean
    @Scope("prototype")
    public SqlMapper sqlMapper(SqlSession sqlSession){
        return new SqlMapper(sqlSession);
    }
}
