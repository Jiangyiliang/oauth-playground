package com.common.util;


import com.alibaba.fastjson.JSON;
import org.springframework.util.DigestUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lego-jspx01 on 2018/2/28.
 */
public class HttpClientUtil {


    public static String postUrl(Map<String,Object> map,String urlstr) {
        String result = null;
        URL url = null;
        HttpURLConnection conn = null;
        StringBuffer sb = new StringBuffer();
        try {
            //创建连接
            url = new URL(urlstr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/json; charset=utf-8");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.connect();
            //json参数
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            String input = JSON.toJSONString(map);
            out.write(input.getBytes());
            out.flush();
            out.close();
            //获取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            reader.close();
            result = sb.toString();
        } catch (Exception e) {
            result = e.getMessage();
        } finally {
            // 关闭连接
            if (null != conn)
                conn.disconnect();
        }
        return result;
    }

    public static String postHeadUrl(Map<String,String> headerMap,Map<String,Object> jsonstr,String urlstr) {
        String result = null;
        URL url = null;
        HttpURLConnection conn = null;
        StringBuffer sb = new StringBuffer();
        try {
            //创建连接
            url = new URL(urlstr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/json; charset=utf-8");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            for (String key : headerMap.keySet()) {
                conn.setRequestProperty(key, headerMap.get(key));
            }
            conn.connect();
            //json参数
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            String input = JSON.toJSONString(jsonstr);
            out.write(input.getBytes());
            out.flush();
            out.close();
            //获取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            reader.close();
            result = sb.toString();
        } catch (Exception e) {
            result = e.getMessage();
        } finally {
            // 关闭连接
            if (null != conn)
                conn.disconnect();
        }
        return result;
    }

}
