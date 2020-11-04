package com.dantefung.sample.teacher.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GenericMapper {

	int selectCount(String sql);

	List<Map> select(String sql);
}
