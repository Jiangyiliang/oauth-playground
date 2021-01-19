package com.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Created by lego-jspx01 on 2020/05/04.
 */
public class LoginTokenInterceptor implements HandlerInterceptor  {

    @Value("${oauth.host}")
    private String oauthHost;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String url = request.getRequestURL().toString();
        if(request.getRequestURI().endsWith(".html")){
            return true;
        }else{
            String access_token = request.getHeader("authorization");
            if(StringUtils.isEmpty(access_token)){//远程校验token
                access_token = request.getParameter("access_token");
            }
            boolean flag =  checkToken(access_token,url);
            if(!flag){
                PrintWriter writer = null;
                String header = request.getHeader("Origin");
                response.setHeader("Access-Control-Allow-Origin", header);
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Headers", "x-access-token");
                response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                try {
                    writer = response.getWriter();
                    writer.print("{\"status\":\"fail\",\"message\":\"没有权限访问\"}");
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    if (writer != null)
                        writer.close();
                }
            }
            return flag;
        }
    }

    /**
     * 该方法将在Controller执行之后，返回视图之前执行，modelAndView表示请求Controller处理之后返回的Model和View对象，所以可以在
     * 这个方法中修改modelAndView的属性，从而达到改变返回的模型和视图的效果。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {

    }


    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {


    }
    private boolean checkToken(String token,String url){
        if(!StringUtils.isEmpty(token)){//校验token
            if(token.startsWith("Bearer "))
                token = token.replace("Bearer","").trim();
            String oauthUrl = oauthHost + "oauth/check_token?token="+token;
            String obj = HttpClientUtil.getUrl(oauthUrl);
            try{
                if(StringUtils.isEmpty(obj))
                    return false;
                System.out.println(obj);
                //{"token_type":"bearer","refresh_token":"d09b8ed0-d289-4d47-8d1e-e57b45ce093a","expires_in":589,"scope":"app"}
                JSONObject jsonObj= JSON.parseObject(obj);
                String scope = jsonObj.getString("scope");
                int expiresIn = jsonObj.getInteger("expires_in");
                if(expiresIn>0 && url.startsWith(scope))
                    return true;
            }catch (Exception e){
                e.printStackTrace();
                return  false;
            }

        }
        return false;
    }
}