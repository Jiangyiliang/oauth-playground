package com.edusoft.sysmanage.mapper;

import com.edusoft.sysmanage.model.ResServiceInfo;
import com.edusoft.sysmanage.model.ResServiceInfoExample;
import com.edusoft.sysmanage.model.ResServiceInfoKey;
import java.util.List;
import java.util.Map;

import com.edusoft.sysmanage.vo.ResServiceInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ResServiceInfoMapper {
    int countByExample(ResServiceInfoExample example);

    int deleteByExample(ResServiceInfoExample example);

    int deleteByPrimaryKey(ResServiceInfoKey key);

    int insert(ResServiceInfo record);

    int insertSelective(ResServiceInfo record);

    List<ResServiceInfo> selectByExampleWithRowbounds(ResServiceInfoExample example, RowBounds rowBounds);

    List<ResServiceInfo> selectByExample(ResServiceInfoExample example);

    ResServiceInfo selectByPrimaryKey(ResServiceInfoKey key);

    int updateByExampleSelective(@Param("record") ResServiceInfo record, @Param("example") ResServiceInfoExample example);

    int updateByExample(@Param("record") ResServiceInfo record, @Param("example") ResServiceInfoExample example);

    int updateByPrimaryKeySelective(ResServiceInfo record);

    int updateByPrimaryKey(ResServiceInfo record);

    //
    List<ResServiceInfoVo> selectVoByExample(Map<String,Object> map);

    ResServiceInfoVo selectVoByPrimaryKey(ResServiceInfoKey key);

}