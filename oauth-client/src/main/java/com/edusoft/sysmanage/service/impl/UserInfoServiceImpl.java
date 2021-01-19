package com.edusoft.sysmanage.service.impl;

import com.common.util.PageQuerySetUtil;
import com.edusoft.sysmanage.mapper.UserInfoMapper;
import com.edusoft.sysmanage.model.UserInfo;
import com.edusoft.sysmanage.model.UserInfoExample;
import com.edusoft.sysmanage.model.UserInfoKey;
import com.edusoft.sysmanage.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lego-jspx01 on 2020/5/12.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Override
    public int insert(UserInfo record) {
        return userInfoMapper.insert(record);
    }

    @Override
    public List<UserInfo> selectByExample(UserInfoExample example, Map<String, Object> map) {
        PageQuerySetUtil.setPageQuery(map);
        return userInfoMapper.selectByExample(example);
    }

    @Override
    public UserInfo selectByPrimaryKey(UserInfoKey key) {
        return userInfoMapper.selectByPrimaryKey(key);
    }

    @Override
    public int updateByPrimaryKeySelective(UserInfo record) {
        return userInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insertReturnId(UserInfo record) {
        return userInfoMapper.insertReturnId(record);
    }

    @Override
    public int insertSpUserId(int userId, int spId) {
        userInfoMapper.deleteSpUserId(userId,spId);
        return userInfoMapper.insertSpUserId(userId,spId);
    }
}
