package com.dantefung.sample.teacher.service;

import com.dantefung.sample.teacher.domain.User;
import java.util.List;
import com.dantefung.sample.teacher.domain.UserExample;
import com.baomidou.mybatisplus.extension.service.IService;
    /**  
* @Title: UserService
* @Description: ${todo}
* @author DANTE FUNG
* @date 2020/4/26 15:16
*/

public interface UserService extends IService<User>{


    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    List<User> selectByExample(UserExample example);

    int updateByExampleSelective(User record,UserExample example);

    int updateByExample(User record,UserExample example);

    int updateBatch(List<User> list);

    int batchInsert(List<User> list);

    int insertOrUpdate(User record);

    int insertOrUpdateSelective(User record);

}
