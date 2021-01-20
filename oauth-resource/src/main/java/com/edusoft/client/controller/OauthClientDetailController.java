package com.edusoft.client.controller;

import com.common.util.resultjson.EntityJsonResult;
import com.common.util.resultjson.JsonResult;
import com.common.util.resultjson.ListJsonResult;
import com.edusoft.client.model.OauthClientDetails;
import com.edusoft.client.model.OauthClientDetailsKey;
import com.edusoft.client.service.OauthClientDetailInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lego-jspx01 on 2020/5/5.
 */
@Api(value = "资源接口管理", description = "资源接口管理",tags = "res-Api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/res")
public class OauthClientDetailController {
    @Autowired
    OauthClientDetailInfoService resSpService;

    @ApiOperation(value = "服务接口信息管理列表")
    @GetMapping(value = "/list")
    public ListJsonResult objList(@RequestParam("offset") Integer offset
            , @RequestParam(defaultValue = "10") Integer limit

    ) {
        Map<String,Object> map = new HashMap<>();
        map.put("offset",offset);
        map.put("limit",limit);
        ListJsonResult json = new ListJsonResult("success","");
        try{
            List<OauthClientDetails> list =  resSpService.selectByExample(null, map);
            PageInfo<OauthClientDetails> page = new PageInfo<OauthClientDetails>(list);
            json.setTotal(page.getTotal());
            json.setData(page.getList());
        }catch (Exception e){
            e.printStackTrace();
            json.setStatus("fail");
            json.setMessage("操作错误："+e.getMessage());
        }
        return json;
    }



    @ApiOperation(value = "资源接口详情")
    @ResponseBody
    @GetMapping(value = "/detail/{clientId}")
    public EntityJsonResult objDetail(@PathVariable String clientId) {
        EntityJsonResult entityJsonResult = new EntityJsonResult("success","");
        try{
            OauthClientDetailsKey key = new OauthClientDetailsKey();
            key.setClientId(clientId);
            OauthClientDetails orderInfo = resSpService.selectByPrimaryKey(key);
            entityJsonResult.setData(orderInfo);
        }catch (Exception e){
            e.printStackTrace();
            entityJsonResult.setStatus("fail");
            entityJsonResult.setMessage("操作错误："+e.getMessage());
        }
        return entityJsonResult;
    }

    @ApiOperation(value = "新增服务接口")
    @PostMapping(value = "/add")
    public EntityJsonResult addEntity(@RequestBody OauthClientDetails orderInfo){
        EntityJsonResult jsonResult = new EntityJsonResult("success","");
        try{
            resSpService.insert(orderInfo);
            jsonResult.setData(orderInfo);
        }catch (Exception e){
            e.printStackTrace();
            jsonResult.setStatus("fail");
            jsonResult.setMessage("操作错误："+e.getMessage());
        }
        return jsonResult;
    }


    @ApiOperation(value = "修改服务接口")
    @ResponseBody
    @PostMapping(value = "/update/{clientId}")
    public JsonResult updateEntity(@PathVariable String clientId, @RequestBody OauthClientDetails orderInfo) {
        JsonResult jsonResult = new JsonResult("success","");
        try{
            orderInfo.setClientId(clientId);
            resSpService.updateByPrimaryKeySelective(orderInfo);
        }catch (Exception e){
            e.printStackTrace();
            jsonResult.setStatus("fail");
            jsonResult.setMessage("操作错误："+e.getMessage());
        }
        return jsonResult;
    }

}
