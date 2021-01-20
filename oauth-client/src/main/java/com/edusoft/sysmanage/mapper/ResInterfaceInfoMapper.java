package com.edusoft.sysmanage.mapper;

import com.edusoft.sysmanage.model.ResInterfaceInfo;
import com.edusoft.sysmanage.model.ResInterfaceInfoExample;
import com.edusoft.sysmanage.model.ResInterfaceInfoKey;
import java.util.List;
import java.util.Map;

import com.edusoft.sysmanage.model.ResServiceInfoKey;
import com.edusoft.sysmanage.vo.ResInterfaceInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ResInterfaceInfoMapper {
    int countByExample(ResInterfaceInfoExample example);

    int deleteByExample(ResInterfaceInfoExample example);

    int deleteByPrimaryKey(ResInterfaceInfoKey key);

    int insert(ResInterfaceInfo record);

    int insertSelective(ResInterfaceInfo record);

    List<ResInterfaceInfo> selectByExampleWithRowbounds(ResInterfaceInfoExample example, RowBounds rowBounds);

    List<ResInterfaceInfo> selectByExample(ResInterfaceInfoExample example);

    ResInterfaceInfo selectByPrimaryKey(ResInterfaceInfoKey key);

    int updateByExampleSelective(@Param("record") ResInterfaceInfo record, @Param("example") ResInterfaceInfoExample example);

    int updateByExample(@Param("record") ResInterfaceInfo record, @Param("example") ResInterfaceInfoExample example);

    int updateByPrimaryKeySelective(ResInterfaceInfo record);

    int updateByPrimaryKey(ResInterfaceInfo record);

    //
    List<ResInterfaceInfoVo> selectVoByExample(Map<String,Object> map);

    ResInterfaceInfoVo selectVoByPrimaryKey(ResInterfaceInfoKey key);
}