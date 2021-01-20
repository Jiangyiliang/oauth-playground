package com.edusoft.sysmanage.service.impl;

import com.common.util.PageQuerySetUtil;
import com.edusoft.sysmanage.mapper.ResSpInfoMapper;
import com.edusoft.sysmanage.model.ResSpInfo;
import com.edusoft.sysmanage.model.ResSpInfoExample;
import com.edusoft.sysmanage.model.ResSpInfoKey;
import com.edusoft.sysmanage.service.ResSpInfoService;
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
public class ResSpInfoServiceImpl implements ResSpInfoService {
    @Autowired
    ResSpInfoMapper resSpInfoMapper;

    @Override
    public int insert(ResSpInfo record) {
        return resSpInfoMapper.insert(record);
    }

    @Override
    public List<ResSpInfo> selectByExample(ResSpInfoExample example, Map<String, Object> map) {
        PageQuerySetUtil.setPageQuery(map);
        return resSpInfoMapper.selectByExample(example);
    }

    @Override
    public ResSpInfo selectByPrimaryKey(ResSpInfoKey key) {
        return resSpInfoMapper.selectByPrimaryKey(key);
    }

    @Override
    public int updateByPrimaryKeySelective(ResSpInfo record) {
        return resSpInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insertReturnId(ResSpInfo record) {
        return resSpInfoMapper.insertReturnId(record);
    }

    @Override
    public ResSpInfo selectBySpName(String name) {
        ResSpInfoExample example = new ResSpInfoExample();
        example.createCriteria().andSignNameEqualTo(name);
        List<ResSpInfo> list = resSpInfoMapper.selectByExample(example);
        if(null!=list && list.size()==1)
            return list.get(0);
        return null;
    }
}
