package com.edusoft.sysmanage.service;

import com.edusoft.sysmanage.model.ResSpInfo;
import com.edusoft.sysmanage.model.ResSpInfoExample;
import com.edusoft.sysmanage.model.ResSpInfoKey;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * Created by lego-jspx01 on 2020/5/4.
 */
public interface ResSpInfoService {

    int insert(ResSpInfo record);

    List<ResSpInfo> selectByExample(ResSpInfoExample example,Map<String,Object> map);

    ResSpInfo selectByPrimaryKey(ResSpInfoKey key);

    int updateByPrimaryKeySelective(ResSpInfo record);

    int insertReturnId(ResSpInfo record);

    ResSpInfo selectBySpName(String name);

}
