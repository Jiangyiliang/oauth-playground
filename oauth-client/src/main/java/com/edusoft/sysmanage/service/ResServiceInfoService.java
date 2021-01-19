package com.edusoft.sysmanage.service;

import com.edusoft.sysmanage.model.ResServiceInfo;
import com.edusoft.sysmanage.model.ResServiceInfoExample;
import com.edusoft.sysmanage.model.ResServiceInfoKey;
import com.edusoft.sysmanage.vo.ResServiceInfoVo;

import java.util.List;
import java.util.Map;

/**
 * Created by lego-jspx01 on 2020/5/4.
 */
public interface ResServiceInfoService {

    int insert(ResServiceInfo record);

    List<ResServiceInfo> selectByExample(ResServiceInfoExample example, Map<String, Object> map);

    ResServiceInfo selectByPrimaryKey(ResServiceInfoKey key);

    int updateByPrimaryKeySelective(ResServiceInfo record);

    List<ResServiceInfoVo> selectVoByExample(Map<String,Object> map);

    ResServiceInfoVo selectVoByPrimaryKey(ResServiceInfoKey key);

}
