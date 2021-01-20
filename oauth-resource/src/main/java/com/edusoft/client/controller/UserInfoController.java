package com.edusoft.client.controller;


import com.common.util.resultjson.EntityJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Api(value = "测试资源接口样例", description = "测试资源接口样例",tags = "res-Api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class UserInfoController {


    @ApiOperation(value = "用户列表样例")
    @GetMapping("/res/getUserInfo")
    public EntityJsonResult getUserInfo(){
        EntityJsonResult json = new EntityJsonResult("success","");
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("realname","张三");
        map.put("username","zhangsan");
        map.put("age","28");
        map.put("tel","15000001111");
        map.put("sfzjh","1504199209110099");
        list.add(map);
        map = new HashMap<>();
        map.put("realname","李四");
        map.put("username","lisi");
        map.put("age","28");
        map.put("tel","13800001111");
        map.put("sfzjh","3504199209110099");
        list.add(map);
        json.setData(list);
        return json;
    }

    @ApiOperation(value = "用户详情样例")
    @GetMapping("/res/userDetail")
    public EntityJsonResult detail(){
        EntityJsonResult json = new EntityJsonResult("success","");
        Map<String,Object> map = new HashMap<>();
        map.put("realname","张三");
        map.put("username","zhangsan");
        map.put("age","28");
        map.put("tel","15000001111");
        map.put("sfzjh","1504199209110099");
        json.setData(map);
        return json;
    }

}
