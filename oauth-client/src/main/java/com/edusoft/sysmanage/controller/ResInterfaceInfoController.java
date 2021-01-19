package com.edusoft.sysmanage.controller;

import com.common.util.Base64Util;
import com.common.util.Constants;
import com.common.util.DateUtils;
import com.common.util.resultjson.EntityJsonResult;
import com.common.util.resultjson.JsonResult;
import com.common.util.resultjson.ListJsonResult;
import com.edusoft.sysmanage.model.*;
import com.edusoft.sysmanage.service.ResInterfaceInfoService;
import com.edusoft.sysmanage.service.ResSpInfoService;
import com.edusoft.sysmanage.vo.ResInterfaceInfoVo;
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

@Api(value = "服务接口管理", description = "服务接口管理",tags = "sys-interface-Api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/sys/interface")
public class ResInterfaceInfoController {
    private static final Log log = LogFactory.getLog(ResInterfaceInfoController.class);
    @Autowired
    ResInterfaceInfoService resSpService;

    @ApiOperation(value = "服务接口信息管理列表")
    @GetMapping(value = "/list")
    public ListJsonResult objList(@RequestParam("offset") Integer offset
            , @RequestParam(defaultValue = "10") Integer limit
            , @RequestParam(required = false) String status
            , @RequestParam(required = false) String spName
            , @RequestParam(required = false) String resName
            , @RequestParam(required = false) String resType
            , @RequestParam(required = false) Integer serviceId
            , @RequestParam(defaultValue = "pxh") String orderByFiled
            , @RequestParam(defaultValue = "asc") String orderByType
    ) {
        Map<String,Object> map = new HashMap<>();
        map.put("offset",offset);
        map.put("limit",limit);
        map.put("status",status);
        map.put("spName",spName);
        map.put("resName",resName);
        map.put("resType", resType);
        map.put("serviceId", serviceId);
        orderByFiled = Base64Util.getLowerField("", orderByFiled);
        map.put("orderByClause", orderByFiled + " " + orderByType);
        ListJsonResult json = new ListJsonResult("success","");
        try{
            List<ResInterfaceInfoVo> list =  resSpService.selectVoByExample(map);
            PageInfo<ResInterfaceInfoVo> page = new PageInfo<ResInterfaceInfoVo>(list);
            json.setTotal(page.getTotal());
            json.setData(page.getList());
        }catch (Exception e){
            e.printStackTrace();
            json.setStatus("fail");
            json.setMessage("操作错误："+e.getMessage());
        }
        return json;
    }



    @ApiOperation(value = "服务接口详情")
    @ResponseBody
    @GetMapping(value = "/detail/{id}")
    public EntityJsonResult objDetail(@PathVariable Integer id) {
        EntityJsonResult entityJsonResult = new EntityJsonResult("success","");
        try{
            ResInterfaceInfoKey key = new ResInterfaceInfoKey();
            key.setId(id);
            ResInterfaceInfoVo orderInfo = resSpService.selectVoByPrimaryKey(key);
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
    public EntityJsonResult addEntity(@RequestBody ResInterfaceInfo orderInfo){
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


    @ApiOperation(value = "修改服务接口")
    @ResponseBody
    @PostMapping(value = "/update/{id}")
    public JsonResult updateEntity(@PathVariable Integer id, @RequestBody ResInterfaceInfo orderInfo) {
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

    @ApiOperation(value = "逻辑删除服务接口")
    @ResponseBody
    @DeleteMapping(value = "/delete/{id}")
    public JsonResult deleteEntity(@PathVariable Integer id) {
        JsonResult jsonResult = new JsonResult("success","");
        try{
            ResInterfaceInfo record = new ResInterfaceInfo();
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
