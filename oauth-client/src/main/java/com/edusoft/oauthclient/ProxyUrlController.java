package com.edusoft.oauthclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.util.HttpUtils;
import com.edusoft.vo.AuthToken;
import com.edusoft.vo.RefreshToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by lego-jspx01 on 2020/12/25.
 */

@Api(value = "请求转发管理", description = "请求转发",tags = "proxy-Api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/proxy")
public class ProxyUrlController {

    @Value("${tmp.path}")
    private String tmppath;


    @Value("${redirect.url}")
    private String redirectUrl;


    @ApiOperation(value = "获取token")
    @PostMapping("/getToken")
    public  Map<String,Object> getToken(HttpServletRequest request,@RequestBody AuthToken authToken){
        Map<String,Object> map =this.getMapByRequest(request);
        String redirectUrl2 = request.getParameter("redirect_uri");
        if(StringUtils.isEmpty(redirectUrl2)){
            redirectUrl2 = redirectUrl ;
        }
        try {
            OAuthClient oAuthClient =new OAuthClient(new URLConnectionClient());
            OAuthClientRequest accessTokenRequest = OAuthClientRequest
                    .tokenLocation(authToken.getTokenUrl())
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(authToken.getClientId())
                    .setClientSecret(authToken.getClientSecret())
                    .setCode(authToken.getCode())
                    .setRedirectURI(redirectUrl2)
                    .buildQueryMessage();
            //去服务端请求access token，并返回响应
            OAuthAccessTokenResponse oAuthResponse =oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
            map.put("resultData", JSONObject.toJSONString(oAuthResponse.getOAuthToken()));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("resultData","error no result");
        }
        return map;
    }

    @ApiOperation(value = "刷新token")
    @PostMapping("/refreshToken")
    public  Map<String,Object> refreshToken(HttpServletRequest request,@RequestBody RefreshToken authToken){
        Map<String,Object> map = this.getMapByRequest(request);
        try {
            OAuthClient oAuthClient =new OAuthClient(new URLConnectionClient());
            OAuthClientRequest accessTokenRequest = OAuthClientRequest
                    .tokenLocation(authToken.getTokenUrl())
                    .setClientId(authToken.getClientId())
                    .setClientSecret(authToken.getClientSecret())
                    .setGrantType(GrantType.REFRESH_TOKEN)
                    .setRefreshToken(authToken.getRefreshToken())
                    .buildQueryMessage();
            //去服务端请求access token，并返回响应
            OAuthAccessTokenResponse oAuthResponse =oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
            map.put("resultData",JSONObject.toJSONString(oAuthResponse.getOAuthToken()));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("resultData","error no result");
        }
        return map;
    }


    @ApiOperation(value = "模拟获取资源")
    @PostMapping("/getRes")
    public Map<String,Object> getRes(HttpServletRequest request,HttpServletResponse response,@RequestBody(required = false) Object datas){
        Map<String,Object> map = this.getMapByRequest(request);
        String method =  request.getParameter("method");
        String contentType = request.getParameter("contentType");
        String url = request.getParameter("url");
        Map<String,String> header = this.getHeadersByRequest(request);
        try{
            if("get".equalsIgnoreCase(method)){
                Object result = HttpUtils.sendGet(url, header);
                map.put("resultData",result);
            }else if ("delete".equalsIgnoreCase(method)){
                Object result = HttpUtils.sendDelete(url, header);
                map.put("resultData",result);
            }else if ("put".equalsIgnoreCase(method)){
                Object result = HttpUtils.sendPut(url, header, contentType, datas);
                map.put("resultData",result);
            }else if ("post".equalsIgnoreCase(method)){
                Object result = HttpUtils.sendPost(url, header, contentType, datas);
                map.put("resultData",result);
            }else if ("patch".equalsIgnoreCase(method)){
                Object result = HttpUtils.sendPatch(url, header, contentType, datas);
                map.put("resultData",result);
            }
        }catch (Exception e){
            map.put("resultData","调用异常"+e.getMessage());
        }
        return map;
    }

    @ApiOperation(value = "模拟获取资源")
    @PostMapping("/getResByFormData")
    public Map<String,Object> getResByFormData(HttpServletRequest request,@RequestParam String url,@RequestParam String method,@RequestParam String contentType){
        Map<String,Object> map = this.getMapByRequest(request);
        try{
            String tmpurl = url;
            String tmpmethod = method;
            String tmpcontentType = contentType;
            Map<String,String> header = this.getHeadersByRequest(request);
            Map<String, Object> params = this.getParamsFromFormDataByNames(request);
            if(tmpurl.indexOf(",")!=-1){
                tmpurl = tmpurl.substring(tmpurl.indexOf(",")+1);
            }else{
                params.remove("url");
            }
            if(tmpmethod.indexOf(",")!=-1){
                tmpmethod = tmpmethod.substring(tmpmethod.indexOf(",")+1);
            }else{
                params.remove("method");
            }
            if(tmpcontentType.indexOf(",")!=-1){
                tmpcontentType = tmpcontentType.substring(tmpcontentType.indexOf(",")+1);
            }else{
                params.remove("contentType");
            }
//            List<MultipartFile> filelist = ((MultipartHttpServletRequest) request).getFiles("");
            MultiValueMap<String, MultipartFile> files = ((MultipartHttpServletRequest) request).getMultiFileMap();
            if ("post".equalsIgnoreCase(tmpmethod)){
                Object result = HttpUtils.sendPost(tmpurl, header, tmppath, params, files);
                map.put("resultData",result);
            }else if ("put".equalsIgnoreCase(tmpmethod)){
                Object result = HttpUtils.sendPut(tmpurl, header, tmppath, params, files);
                map.put("resultData",result);
            }else if ("patch".equalsIgnoreCase(tmpmethod)){
                Object result = HttpUtils.sendPatch(tmpurl, header, tmppath, params, files);
                map.put("resultData",result);
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("resultData", "调用异常" + e.getMessage());
        }
        return map;
    }



    private String NAME = ",method,contentType,url,";

    private Map<String, Object> getParamsFromFormDataByNames(HttpServletRequest request){
        Map<String, Object> map =new HashMap<>();
        Enumeration<String> er = request.getParameterNames();
        while (er.hasMoreElements()) {
            String name = (String) er.nextElement();
//            if(NAME.indexOf(","+name+",")==-1){
                String value = request.getParameter(name);
                map.put(name, value);
//            }
        }
        return map;
    }

    private Map<String,Object> getMapByRequest(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        Enumeration e1 = request.getHeaderNames();
        while (e1.hasMoreElements()) {
            String headerName = (String) e1.nextElement();
            headerName = headerName.toLowerCase();
            if(!headerName.startsWith("x-access-")){
                String headValue = request.getHeader(headerName);
                map.put(headerName,headValue);
            }
        }
        return map;
    }

    private Map<String,String> getHeadersByRequest(HttpServletRequest request){
        Map<String,String> map = new HashMap<>();
        Enumeration e1 = request.getHeaderNames();
        while (e1.hasMoreElements()) {
            String headerName = (String) e1.nextElement();
            headerName = headerName.toLowerCase();
            if(headerName.startsWith("x-access-")){
                String headValue = request.getHeader(headerName);
                map.put(headerName.replace("x-access-",""),headValue);
            }
        }
        return map;
    }
}
