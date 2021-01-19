package com.edusoft.oauthclient;

import com.common.util.Constants;
import com.common.util.resultjson.ListJsonResult;
import com.common.util.resultjson.ListJsonResult2;
import com.edusoft.sysmanage.model.*;
import com.edusoft.sysmanage.service.ResInterfaceInfoService;
import com.edusoft.sysmanage.service.ResServiceInfoService;
import com.edusoft.sysmanage.service.ResSpInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "playground", description = "playground管理",tags = "front-Api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/front/api")
public class PlayGroudController {
    @Autowired
    ResSpInfoService resSpService;
    @Autowired
    ResServiceInfoService resServiceInfoService;
    @Autowired
    ResInterfaceInfoService resInterfaceInfoService;

    @ApiOperation(value = "所有服务商列表")
    @GetMapping("/spList")
    public ListJsonResult spList(){
        ListJsonResult json = new ListJsonResult("success","");
        try{
            ResSpInfoExample example = new ResSpInfoExample();
            ResSpInfoExample.Criteria criteria = example.createCriteria();
            criteria.andDeletedEqualTo(Constants.DELETE_DEFAULT);
            example.setOrderByClause(" id asc ");
            List<ResSpInfo> list =  resSpService.selectByExample(example,null);
            if(null!=list){
                json.setTotal(list.size());
            }
            json.setData(list);
        }catch (Exception e){
            e.printStackTrace();
            json.setStatus("fail");
            json.setMessage("操作错误："+e.getMessage());
        }
        return json;
    }

    @ApiOperation(value = "某服务商下所有列表")
    @GetMapping("/serviceList/{spId}")
    public ListJsonResult serviceList(@PathVariable Integer spId){
        ListJsonResult json = new ListJsonResult("success","");
        try{
            ResServiceInfoExample example = new ResServiceInfoExample();
            ResServiceInfoExample.Criteria criteria = example.createCriteria();
            criteria.andDeletedEqualTo(Constants.DELETE_DEFAULT).andSpIdEqualTo(spId);
            example.setOrderByClause(" pxh asc ");
            List<ResServiceInfo> list =  resServiceInfoService.selectByExample(example, null);
            if(null!=list){
                json.setTotal(list.size());
            }
            json.setData(list);
        }catch (Exception e){
            e.printStackTrace();
            json.setStatus("fail");
            json.setMessage("操作错误："+e.getMessage());
        }
        return json;
    }

    @ApiOperation(value = "某服务下所有资源接口列表")
    @GetMapping("/resList/{serviceId}")
    public ListJsonResult resList(@PathVariable Integer serviceId){
        ListJsonResult json = new ListJsonResult("success","");
        try{
            ResInterfaceInfoExample example = new ResInterfaceInfoExample();
            ResInterfaceInfoExample.Criteria criteria = example.createCriteria();
            criteria.andDeletedEqualTo(Constants.DELETE_DEFAULT).andServiceIdEqualTo(serviceId);
            example.setOrderByClause(" pxh asc ");
            List<ResInterfaceInfo> list =  resInterfaceInfoService.selectByExample(example,null);
            if(null!=list){
                json.setTotal(list.size());
            }
            json.setData(list);
        }catch (Exception e){
            e.printStackTrace();
            json.setStatus("fail");
            json.setMessage("操作错误："+e.getMessage());
        }
        return json;
    }

}
