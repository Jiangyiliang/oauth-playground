package com.edusoft.oauthclient;

import com.alibaba.fastjson.JSONObject;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/front")
public class ClientController {
    @Value("${oauth.host}")
    private String oauthHost;
    @Value("${oauth.client}")
    private String client;
    @Value("${oauth.secret}")
    private String secret;
    @Value("${redirect.url}")
    private String redirectUrl;



    private Map<String,Object> getMapByRequest(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        Enumeration e1 = request.getHeaderNames();
        while (e1.hasMoreElements()) {
            String headerName = (String) e1.nextElement();
            String headValue = request.getHeader(headerName);
            map.put(headerName,headValue);
        }
        return map;
    }

    @GetMapping("/getToken")
    @ResponseBody
    public String getAccessToken(HttpServletRequest request, HttpServletResponse response){
        System.out.println("+++++++++++++++++++++++++++====getToken============================");
        String code = request.getParameter("code");
        String redirectUrl = request.getParameter("redirect_uri");
        if(StringUtils.isEmpty(redirectUrl)){
            redirectUrl = redirectUrl;
        }
        Map<String,Object> map = this.getMapByRequest(request);
        try {
            OAuthClient oAuthClient =new OAuthClient(new URLConnectionClient());
            //String secodeurl = "http://localhost:8080/oauth/token?grant_type=authorization_code&code="+code+"&client_id=dev&client_secret=dev&redirect_uri=http://localhost:8888/index";
            OAuthClientRequest accessTokenRequest = OAuthClientRequest
                    .tokenLocation(oauthHost+"oauth/token")
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(client)
                    .setClientSecret(secret)
                    .setCode(code)
                    .setRedirectURI(redirectUrl)
                    .buildQueryMessage();
            //去服务端请求access token，并返回响应
            OAuthAccessTokenResponse oAuthResponse =oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
            map.put("resultData",JSONObject.toJSONString(oAuthResponse.getOAuthToken()));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("resultData","error no result");
        }
        return JSONObject.toJSONString(map);
    }

    @GetMapping("/refreshToken")
    @ResponseBody
    public String getRefreshToken(HttpServletRequest request, HttpServletResponse response){
        System.out.println("+++++++++++++++++++++++++++====getRefreshToken============================");
        String refreshToken = request.getParameter("refreshToken");
        Map<String,Object> map = this.getMapByRequest(request);
        try {
            OAuthClient oAuthClient =new OAuthClient(new URLConnectionClient());
            OAuthClientRequest accessTokenRequest = OAuthClientRequest
                    .tokenLocation(oauthHost+"oauth/token")
                    .setClientId(client)
                    .setClientSecret(secret)
                    .setGrantType(GrantType.REFRESH_TOKEN)
                    .setRefreshToken(refreshToken)
                    .buildQueryMessage();
            //去服务端请求access token，并返回响应
            OAuthAccessTokenResponse oAuthResponse =oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
            map.put("resultData",JSONObject.toJSONString(oAuthResponse.getOAuthToken()));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("resultData","error no result");
        }
        return JSONObject.toJSONString(map);
    }


}
