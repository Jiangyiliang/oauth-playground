package com.edusoft.sysmanage.mapper;

import com.edusoft.sysmanage.model.ResSpInfo;
import com.edusoft.sysmanage.model.ResSpInfoExample;
import com.edusoft.sysmanage.model.ResSpInfoKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ResSpInfoMapper {
    int countByExample(ResSpInfoExample example);

    int deleteByExample(ResSpInfoExample example);

    int deleteByPrimaryKey(ResSpInfoKey key);

    int insert(ResSpInfo record);

    int insertSelective(ResSpInfo record);

    List<ResSpInfo> selectByExampleWithRowbounds(ResSpInfoExample example, RowBounds rowBounds);

    List<ResSpInfo> selectByExample(ResSpInfoExample example);

    ResSpInfo selectByPrimaryKey(ResSpInfoKey key);

    int updateByExampleSelective(@Param("record") ResSpInfo record, @Param("example") ResSpInfoExample example);

    int updateByExample(@Param("record") ResSpInfo record, @Param("example") ResSpInfoExample example);

    int updateByPrimaryKeySelective(ResSpInfo record);

    int updateByPrimaryKey(ResSpInfo record);
    //
    int insertReturnId(ResSpInfo record);
}