package com.edusoft.sysmanage.service.impl;

import com.common.util.PageQuerySetUtil;
import com.edusoft.sysmanage.mapper.ResServiceInfoMapper;
import com.edusoft.sysmanage.mapper.ResSpInfoMapper;
import com.edusoft.sysmanage.model.*;
import com.edusoft.sysmanage.service.ResServiceInfoService;
import com.edusoft.sysmanage.vo.ResServiceInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by lego-jspx01 on 2020/5/4.
 */
@Service
@Transactional
public class ResServiceInfoServiceImpl implements ResServiceInfoService {
    @Autowired
    ResServiceInfoMapper resServiceInfoMapper;

    @Override
    public int insert(ResServiceInfo record) {
        return resServiceInfoMapper.insert(record);
    }

    @Override
    public List<ResServiceInfo> selectByExample(ResServiceInfoExample example, Map<String, Object> map) {
        PageQuerySetUtil.setPageQuery(map);
        return resServiceInfoMapper.selectByExample(example);
    }

    @Override
    public ResServiceInfo selectByPrimaryKey(ResServiceInfoKey key) {
        return resServiceInfoMapper.selectByPrimaryKey(key);
    }

    @Override
    public int updateByPrimaryKeySelective(ResServiceInfo record) {
        return resServiceInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<ResServiceInfoVo> selectVoByExample(Map<String, Object> map) {
        PageQuerySetUtil.setPageQuery(map);
        return resServiceInfoMapper.selectVoByExample(map);
    }

    @Override
    public ResServiceInfoVo selectVoByPrimaryKey(ResServiceInfoKey key) {
        return resServiceInfoMapper.selectVoByPrimaryKey(key);
    }
}
