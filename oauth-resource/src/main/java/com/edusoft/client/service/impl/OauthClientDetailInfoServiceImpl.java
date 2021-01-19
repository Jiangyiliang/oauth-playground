package com.edusoft.client.service.impl;

import com.common.util.PageQuerySetUtil;
import com.edusoft.client.mapper.OauthClientDetailsMapper;
import com.edusoft.client.model.OauthClientDetails;
import com.edusoft.client.model.OauthClientDetailsExample;
import com.edusoft.client.model.OauthClientDetailsKey;
import com.edusoft.client.service.OauthClientDetailInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lego-jspx01 on 2020/5/5.
 */
@Service
public class OauthClientDetailInfoServiceImpl implements OauthClientDetailInfoService {

    @Autowired
    private OauthClientDetailsMapper oauthClientDetailsMapper;

    @Override
    public int insert(OauthClientDetails record) {
        return oauthClientDetailsMapper.insert(record);
    }

    @Override
    public List<OauthClientDetails> selectByExample(OauthClientDetailsExample example, Map<String, Object> map) {
        PageQuerySetUtil.setPageQuery(map);
        return oauthClientDetailsMapper.selectByExample(example);
    }

    @Override
    public OauthClientDetails selectByPrimaryKey(OauthClientDetailsKey key) {
        return oauthClientDetailsMapper.selectByPrimaryKey(key);
    }

    @Override
    public int updateByPrimaryKeySelective(OauthClientDetails record) {
        return oauthClientDetailsMapper.updateByPrimaryKeySelective(record);
    }
}
