package com.dantefung.sample.teacher.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dantefung.sample.teacher.domain.User;
import com.dantefung.sample.teacher.domain.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**  
* @Title: UserMapper
* @Description: ${todo}
* @author DANTE FUNG
* @date 2020/4/26 15:16
*/

public interface UserMapper extends BaseMapper<User> {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    List<User> selectByExample(UserExample example);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateBatch(List<User> list);

    int batchInsert(@Param("list") List<User> list);

    int insertOrUpdate(User record);

    int insertOrUpdateSelective(User record);
}