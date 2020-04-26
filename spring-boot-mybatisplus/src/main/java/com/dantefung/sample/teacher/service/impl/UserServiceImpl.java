package com.dantefung.sample.teacher.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dantefung.sample.teacher.domain.User;
import com.dantefung.sample.teacher.domain.UserExample;
import com.dantefung.sample.mapper.UserMapper;
import com.dantefung.sample.teacher.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
/**  
* @Title: UserServiceImpl
* @Description: ${todo}
* @author DANTE FUNG
* @date 2020/4/26 15:16
*/

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Override
    public long countByExample(UserExample example) {
        return baseMapper.countByExample(example);
    }
    @Override
    public int deleteByExample(UserExample example) {
        return baseMapper.deleteByExample(example);
    }
    @Override
    public List<User> selectByExample(UserExample example) {
        return baseMapper.selectByExample(example);
    }
    @Override
    public int updateByExampleSelective(User record,UserExample example) {
        return baseMapper.updateByExampleSelective(record,example);
    }
    @Override
    public int updateByExample(User record,UserExample example) {
        return baseMapper.updateByExample(record,example);
    }
    @Override
    public int updateBatch(List<User> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<User> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(User record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(User record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
