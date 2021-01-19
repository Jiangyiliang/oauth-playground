package com.edusoft.sysmanage.controller;

import com.common.util.Base64Util;
import com.common.util.Constants;
import com.common.util.DateUtils;
import com.common.util.resultjson.EntityJsonResult;
import com.common.util.resultjson.JsonResult;
import com.common.util.resultjson.ListJsonResult;
import com.edusoft.sysmanage.model.*;
import com.edusoft.sysmanage.service.ResSpInfoService;
import com.edusoft.sysmanage.service.UserInfoService;
import com.edusoft.sysmanage.vo.SignUserVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "用户管理", description = "用户管理",tags = "sys-userApi", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/sys/user")
public class UserInfoController {
    private static final Log log = LogFactory.getLog(UserInfoController.class);
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    ResSpInfoService resSpService;

    @ApiOperation(value = "用户信息管理列表")
    @GetMapping(value = "/list")
    public ListJsonResult objList(@RequestParam("offset") Integer offset
            , @RequestParam(defaultValue = "10") Integer limit
            , @RequestParam(required = false) String gender
            , @RequestParam(required = false) String realname
            , @RequestParam(required = false) String username
            , @RequestParam(required = false) String status
            , @RequestParam(required = false) String userType
            , @RequestParam(defaultValue = "id") String orderByFiled
            , @RequestParam(defaultValue = "desc") String orderByType
    ) {
        Map<String,Object> map = new HashMap<>();
        map.put("offset",offset);
        map.put("limit",limit);
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(gender)){
            criteria.andGenderEqualTo(gender);
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }
        if(!StringUtils.isEmpty(userType)){
            criteria.andUserTypeEqualTo(userType);
        }
        if(!StringUtils.isEmpty(realname)){
            criteria.andRealnameLike("%" + realname + "%");
        }
        if(!StringUtils.isEmpty(username)){
            criteria.andUsernameLike("%" + username + "%");
        }
        criteria.andDeletedEqualTo(Constants.DELETE_DEFAULT);
        orderByFiled = Base64Util.getLowerField("", orderByFiled);
        example.setOrderByClause(orderByFiled + " " + orderByType);
        ListJsonResult json = new ListJsonResult("success","");
        try{
            List<UserInfo> list =  userInfoService.selectByExample(example,map);
            PageInfo<UserInfo> page = new PageInfo<UserInfo>(list);
            json.setTotal(page.getTotal());
            json.setData(page.getList());
        }catch (Exception e){
            e.printStackTrace();
            json.setStatus("fail");
            json.setMessage("操作错误："+e.getMessage());
        }
        return json;
    }



    @ApiOperation(value = "用户详情")
    @ResponseBody
    @GetMapping(value = "/detail/{id}")
    public EntityJsonResult objDetail(@PathVariable Integer id) {
        EntityJsonResult entityJsonResult = new EntityJsonResult("success","");
        try{
            UserInfoKey key = new UserInfoKey();
            key.setId(id);
            UserInfo orderInfo = userInfoService.selectByPrimaryKey(key);
            entityJsonResult.setData(orderInfo);
        }catch (Exception e){
            e.printStackTrace();
            entityJsonResult.setStatus("fail");
            entityJsonResult.setMessage("操作错误："+e.getMessage());
        }
        return entityJsonResult;
    }

    @ApiOperation(value = "新增用户")
    @PostMapping(value = "/add")
    public EntityJsonResult addEntity(@RequestBody UserInfo orderInfo){
        EntityJsonResult jsonResult = new EntityJsonResult("success","");
        try{
//            orderInfo.setAddUser(JwtUtil.getUserName());
            orderInfo.setPwd(Base64Util.EncoderByMd5(orderInfo.getPwd()));
            orderInfo.setDeleted(Constants.DELETE_DEFAULT);
            orderInfo.setAddTime(DateUtils.getNowDate());
            orderInfo.setUpdateUser(orderInfo.getAddUser());
            orderInfo.setUpdateTime(orderInfo.getAddTime());
            userInfoService.insert(orderInfo);
            jsonResult.setData(orderInfo);
        }catch (Exception e){
            e.printStackTrace();
            jsonResult.setStatus("fail");
            jsonResult.setMessage("操作错误："+e.getMessage());
        }
        return jsonResult;
    }


    @ApiOperation(value = "修改用户")
    @ResponseBody
    @PostMapping(value = "/update/{id}")
    public JsonResult updateEntity(@PathVariable Integer id, @RequestBody UserInfo orderInfo) {
        JsonResult jsonResult = new JsonResult("success","");
        try{
            orderInfo.setUpdateTime(DateUtils.getNowDate());
//            orderInfo.setUpdateUser(JwtUtil.getUserName());
            orderInfo.setId(id);
            userInfoService.updateByPrimaryKeySelective(orderInfo);
        }catch (Exception e){
            e.printStackTrace();
            jsonResult.setStatus("fail");
            jsonResult.setMessage("操作错误："+e.getMessage());
        }
        return jsonResult;
    }

    @ApiOperation(value = "逻辑删除用户")
    @ResponseBody
    @DeleteMapping(value = "/delete/{id}")
    public JsonResult deleteEntity(@PathVariable Integer id) {
        JsonResult jsonResult = new JsonResult("success","");
        try{
            UserInfo record = new UserInfo();
            record.setId(id);
            record.setDeleted(Constants.IS_DELETED);
            record.setUpdateTime(DateUtils.getNowDate());
//            record.setUpdateUser("");
            userInfoService.updateByPrimaryKeySelective(record);
        }catch (Exception e){
            e.printStackTrace();
            jsonResult.setStatus("fail");
            jsonResult.setMessage("操作错误："+e.getMessage());
        }
        return jsonResult;
    }

    @ApiOperation(value = "注册用户")
    @PostMapping(value = "/signUser")
    public EntityJsonResult addSignEntity(@RequestBody SignUserVo signUserVo){
        EntityJsonResult jsonResult = new EntityJsonResult("success","");
        try{
            UserInfo userInfo = new  UserInfo();
            BeanUtils.copyProperties(signUserVo, userInfo);
            userInfo.setPwd(Base64Util.EncoderByMd5(userInfo.getPwd()));
            userInfo.setDeleted(Constants.DELETE_DEFAULT);
            userInfo.setAddTime(DateUtils.getNowDate());
            userInfo.setUpdateUser(userInfo.getAddUser());
            userInfo.setUpdateTime(userInfo.getAddTime());
            userInfoService.insertReturnId(userInfo);
            if(signUserVo.getSpId()!=0){
                userInfoService.insertSpUserId(userInfo.getId(),signUserVo.getSpId());
            }else{
                //查询插入服务商
                if(StringUtils.isNotEmpty(signUserVo.getSpName())){
                    ResSpInfo orderInfo = resSpService.selectBySpName(signUserVo.getSpName());
                    if(null == orderInfo ){
                        orderInfo = new ResSpInfo();
                        orderInfo.setName(signUserVo.getSpName());
                        orderInfo.setDeleted(Constants.DELETE_DEFAULT);
                        orderInfo.setAddTime(DateUtils.getNowDate());
                        orderInfo.setUpdateUser(orderInfo.getAddUser());
                        orderInfo.setUpdateTime(orderInfo.getAddTime());
                        resSpService.insertReturnId(orderInfo);
                    }
                    //管理服务和用户
                    userInfoService.insertSpUserId(userInfo.getId(),orderInfo.getId());
                }
            }

            signUserVo.setId(userInfo.getId());
            jsonResult.setData(signUserVo);
        }catch (Exception e){
            e.printStackTrace();
            jsonResult.setStatus("fail");
            jsonResult.setMessage("操作错误："+e.getMessage());
        }
        return jsonResult;
    }

    @ApiOperation(value = "用户绑定服务商")
    @ResponseBody
    @PostMapping(value = "/binding/{userId}")
    public JsonResult updateEntity(@PathVariable Integer userId, @RequestParam Integer spId) {
        JsonResult jsonResult = new JsonResult("success","");
        try{
            userInfoService.insertSpUserId(userId, spId);
        }catch (Exception e){
            e.printStackTrace();
            jsonResult.setStatus("fail");
            jsonResult.setMessage("操作错误："+e.getMessage());
        }
        return jsonResult;
    }

}
