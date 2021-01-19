package com.edusoft.sysmanage.controller;

import com.common.util.Base64Util;
import com.common.util.Constants;
import com.common.util.DateUtils;
import com.common.util.resultjson.EntityJsonResult;
import com.common.util.resultjson.JsonResult;
import com.common.util.resultjson.ListJsonResult;
import com.edusoft.sysmanage.model.ResSpInfo;
import com.edusoft.sysmanage.model.ResSpInfoExample;
import com.edusoft.sysmanage.model.ResSpInfoKey;
import com.edusoft.sysmanage.service.ResSpInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "服务商管理", description = "服务商管理",tags = "sys-spApi", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/sys/sp")
public class ResSpInfoController {
    private static final Log log = LogFactory.getLog(ResSpInfoController.class);
    @Autowired
    ResSpInfoService resSpService;

    @ApiOperation(value = "服务商信息管理列表")
    @GetMapping(value = "/list")
    public ListJsonResult objList(@RequestParam("offset") Integer offset
            , @RequestParam(defaultValue = "10") Integer limit
            , @RequestParam(required = false) String status
            , @RequestParam(required = false) String code
            , @RequestParam(required = false) String name
            , @RequestParam(required = false) String username
            , @RequestParam(defaultValue = "id") String orderByFiled
            , @RequestParam(defaultValue = "desc") String orderByType
    ) {
        Map<String,Object> map = new HashMap<>();
        map.put("offset",offset);
        map.put("limit",limit);
        ResSpInfoExample example = new ResSpInfoExample();
        ResSpInfoExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(code)){
            criteria.andSignUuidLike("%" + code + "%");
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(username)){
            criteria.andSignNameLike("%" + username + "%");
        }
        criteria.andDeletedEqualTo(Constants.DELETE_DEFAULT);
        orderByFiled = Base64Util.getLowerField("", orderByFiled);
        example.setOrderByClause(orderByFiled + " " + orderByType);
        ListJsonResult json = new ListJsonResult("success","");
        try{
            List<ResSpInfo> list =  resSpService.selectByExample(example,map);
            PageInfo<ResSpInfo> page = new PageInfo<ResSpInfo>(list);
            json.setTotal(page.getTotal());
            json.setData(page.getList());
        }catch (Exception e){
            e.printStackTrace();
            json.setStatus("fail");
            json.setMessage("操作错误："+e.getMessage());
        }
        return json;
    }



    @ApiOperation(value = "服务商详情")
    @ResponseBody
    @GetMapping(value = "/detail/{id}")
    public EntityJsonResult objDetail(@PathVariable Integer id) {
        EntityJsonResult entityJsonResult = new EntityJsonResult("success","");
        try{
            ResSpInfoKey key = new ResSpInfoKey();
            key.setId(id);
            ResSpInfo orderInfo = resSpService.selectByPrimaryKey(key);
            entityJsonResult.setData(orderInfo);
        }catch (Exception e){
            e.printStackTrace();
            entityJsonResult.setStatus("fail");
            entityJsonResult.setMessage("操作错误："+e.getMessage());
        }
        return entityJsonResult;
    }

    @ApiOperation(value = "新增服务商")
    @PostMapping(value = "/add")
    public EntityJsonResult addEntity(@RequestBody ResSpInfo orderInfo){
        EntityJsonResult jsonResult = new EntityJsonResult("success","");
        try{
//            orderInfo.setAddUser(JwtUtil.getUserName());
            orderInfo.setDeleted(Constants.DELETE_DEFAULT);
            orderInfo.setAddTime(DateUtils.getNowDate());
            orderInfo.setUpdateUser(orderInfo.getAddUser());
            orderInfo.setUpdateTime(orderInfo.getAddTime());
            resSpService.insert(orderInfo);
            jsonResult.setData(orderInfo);
        }catch (Exception e){
            e.printStackTrace();
            jsonResult.setStatus("fail");
            jsonResult.setMessage("操作错误："+e.getMessage());
        }
        return jsonResult;
    }


    @ApiOperation(value = "修改服务商")
    @ResponseBody
    @PostMapping(value = "/update/{id}")
    public JsonResult updateEntity(@PathVariable Integer id, @RequestBody ResSpInfo orderInfo) {
        JsonResult jsonResult = new JsonResult("success","");
        try{
            orderInfo.setUpdateTime(DateUtils.getNowDate());
//            orderInfo.setUpdateUser(JwtUtil.getUserName());
            orderInfo.setId(id);
            resSpService.updateByPrimaryKeySelective(orderInfo);
        }catch (Exception e){
            e.printStackTrace();
            jsonResult.setStatus("fail");
            jsonResult.setMessage("操作错误："+e.getMessage());
        }
        return jsonResult;
    }

    @ApiOperation(value = "逻辑删除服务商")
    @ResponseBody
    @DeleteMapping(value = "/delete/{id}")
    public JsonResult deleteEntity(@PathVariable Integer id) {
        JsonResult jsonResult = new JsonResult("success","");
        try{
            ResSpInfo record = new ResSpInfo();
            record.setId(id);
            record.setDeleted(Constants.IS_DELETED);
            record.setUpdateTime(DateUtils.getNowDate());
//            record.setUpdateUser("");
            resSpService.updateByPrimaryKeySelective(record);
        }catch (Exception e){
            e.printStackTrace();
            jsonResult.setStatus("fail");
            jsonResult.setMessage("操作错误："+e.getMessage());
        }
        return jsonResult;
    }
}
