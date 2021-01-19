package com.edusoft.sysmanage.mapper;

import com.edusoft.sysmanage.model.UserInfo;
import com.edusoft.sysmanage.model.UserInfoExample;
import com.edusoft.sysmanage.model.UserInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserInfoMapper {
    int countByExample(UserInfoExample example);

    int deleteByExample(UserInfoExample example);

    int deleteByPrimaryKey(UserInfoKey key);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    List<UserInfo> selectByExampleWithRowbounds(UserInfoExample example, RowBounds rowBounds);

    List<UserInfo> selectByExample(UserInfoExample example);

    UserInfo selectByPrimaryKey(UserInfoKey key);

    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
    //
    int insertReturnId(UserInfo record);

    int deleteSpUserId(@Param("userId") int userId,@Param("spId") int spId);
    int insertSpUserId(@Param("userId") int userId,@Param("spId") int spId);
}