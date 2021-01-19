package com.edusoft.client.service;

import com.edusoft.client.model.OauthClientDetails;
import com.edusoft.client.model.OauthClientDetailsExample;
import com.edusoft.client.model.OauthClientDetailsKey;


import java.util.List;
import java.util.Map;

/**
 * Created by lego-jspx01 on 2020/5/4.
 */
public interface OauthClientDetailInfoService {

    int insert(OauthClientDetails record);

    List<OauthClientDetails> selectByExample(OauthClientDetailsExample example, Map<String, Object> map);

    OauthClientDetails selectByPrimaryKey(OauthClientDetailsKey key);

    int updateByPrimaryKeySelective(OauthClientDetails record);

}
