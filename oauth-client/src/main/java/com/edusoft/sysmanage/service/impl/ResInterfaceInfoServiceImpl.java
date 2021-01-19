package com.edusoft.sysmanage.service.impl;

import com.common.util.PageQuerySetUtil;
import com.edusoft.sysmanage.mapper.ResInterfaceInfoMapper;
import com.edusoft.sysmanage.model.*;
import com.edusoft.sysmanage.service.ResInterfaceInfoService;
import com.edusoft.sysmanage.vo.ResInterfaceInfoVo;
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
public class ResInterfaceInfoServiceImpl implements ResInterfaceInfoService {
    @Autowired
    ResInterfaceInfoMapper resInterfaceInfoMapper;

    @Override
    public int insert(ResInterfaceInfo record) {
        return resInterfaceInfoMapper.insert(record);
    }

    @Override
    public List<ResInterfaceInfo> selectByExample(ResInterfaceInfoExample example, Map<String, Object> map) {
        PageQuerySetUtil.setPageQuery(map);
        return resInterfaceInfoMapper.selectByExample(example);
    }

    @Override
    public ResInterfaceInfo selectByPrimaryKey(ResInterfaceInfoKey key) {
        return resInterfaceInfoMapper.selectByPrimaryKey(key);
    }

    @Override
    public int updateByPrimaryKeySelective(ResInterfaceInfo record) {
        return resInterfaceInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<ResInterfaceInfoVo> selectVoByExample(Map<String, Object> map) {
        PageQuerySetUtil.setPageQuery(map);
        return resInterfaceInfoMapper.selectVoByExample(map);
    }

    @Override
    public ResInterfaceInfoVo selectVoByPrimaryKey(ResInterfaceInfoKey key) {
        return resInterfaceInfoMapper.selectVoByPrimaryKey(key);
    }
}
