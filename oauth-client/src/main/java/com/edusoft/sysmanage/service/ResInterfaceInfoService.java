package com.edusoft.sysmanage.service;

import com.edusoft.sysmanage.model.*;
import com.edusoft.sysmanage.vo.ResInterfaceInfoVo;
import com.edusoft.sysmanage.vo.ResServiceInfoVo;

import java.util.List;
import java.util.Map;

/**
 * Created by lego-jspx01 on 2020/5/4.
 */
public interface ResInterfaceInfoService {

    int insert(ResInterfaceInfo record);

    List<ResInterfaceInfo> selectByExample(ResInterfaceInfoExample example, Map<String, Object> map);

    ResInterfaceInfo selectByPrimaryKey(ResInterfaceInfoKey key);

    int updateByPrimaryKeySelective(ResInterfaceInfo record);

    List<ResInterfaceInfoVo> selectVoByExample(Map<String,Object> map);

    ResInterfaceInfoVo selectVoByPrimaryKey(ResInterfaceInfoKey key);

}
