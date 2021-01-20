package com.edusoft.sysmanage.service;

import com.edusoft.sysmanage.model.UserInfo;
import com.edusoft.sysmanage.model.UserInfoExample;
import com.edusoft.sysmanage.model.UserInfoKey;

import java.util.List;
import java.util.Map;

/**
 * Created by lego-jspx01 on 2020/5/4.
 */
public interface UserInfoService {

    int insert(UserInfo record);

    List<UserInfo> selectByExample(UserInfoExample example, Map<String, Object> map);

    UserInfo selectByPrimaryKey(UserInfoKey key);

    int updateByPrimaryKeySelective(UserInfo record);

    int insertReturnId(UserInfo record);

    int insertSpUserId(int userId,int spId);

}
