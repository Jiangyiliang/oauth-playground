package com.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * HttpClient发送GET、POST请求
 */
public class HttpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
    /**
     * 返回成功状态码
     */
    private static final int SUCCESS_CODE = 200;

    /**
     * 发送GET请求
     * @param url   请求url
     * @param headers   请求参数
     * @return JSON或者字符串
     * @throws Exception
     */
    public static Object sendDelete(String url,Map<String,String> headers) throws Exception{
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            client = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder(url);
            HttpDelete httpGet = new HttpDelete(uriBuilder.build());
            if(null != headers){
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
                }
            }
            response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode){
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity,"UTF-8");
                try{
                    jsonObject = JSONObject.parseObject(result);
                    return jsonObject;
                }catch (Exception e){
                    return result;
                }
            }else{
                LOGGER.error("HttpClientService-line: {}, errorMsg{}", 97, "delete请求失败！");
            }
        }catch (Exception e){
            LOGGER.error("HttpClientService-line: {}, Exception: {}", 100, e);
        } finally {
            response.close();
            client.close();
        }
        return null;
    }

    /**
     * 发送GET请求
     * @param url   请求url
     * @param headers   请求参数
     * @return JSON或者字符串
     * @throws Exception
     */
    public static Object sendGet(String url,Map<String,String> headers) throws Exception{
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            client = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder(url);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            if(null != headers){
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
                }
            }
            response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode){
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity,"UTF-8");
                try{
                    jsonObject = JSONObject.parseObject(result);
                    return jsonObject;
                }catch (Exception e){
                    return result;
                }
            }else{
                LOGGER.error("HttpClientService-line: {}, errorMsg{}", 97, "GET请求失败！");
            }
        }catch (Exception e){
            LOGGER.error("HttpClientService-line: {}, Exception: {}", 100, e);
        } finally {
            response.close();
            client.close();
        }
        return null;
    }

    /**
     * json
     * @param data
     * @return
     */
    public static StringEntity getByData(Object data){
        try{
            String json = JSONObject.toJSONString(data);
            StringEntity se  =  new StringEntity(json);
            se.setContentType("application/json");//发送json数据需要设置contentType
            se.setContentEncoding("UTF-8");
            return se;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * urlencoded
     * @param data
     * @return
     */
    public static UrlEncodedFormEntity getUrlEncodeByData(Object data){
        try{
            Map<String,String> map = JSONObject.parseObject(JSONObject.toJSONString(data), Map.class);
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            if (map.size() != 0) {
                // 将mapdata中的key存在set集合中，通过迭代器取出所有的key，再获取每一个键对应的值
                Set keySet = map.keySet();
                Iterator it = keySet.iterator();
                while (it.hasNext()) {
                    String k =  it.next().toString();// key
                    String v = map.get(k);// value
                    nameValuePairs.add(new BasicNameValuePair(k, v));
                }
            }
            return new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
        }catch (Exception e){
            return null;
        }
    }

    /**
     * text/plain
     * @param data
     * @return
     */
    public static StringEntity getTextPlainByData(Object data){
        try{
            String json = JSONObject.toJSONString(data);
            StringEntity se  =  new StringEntity(json);
            return se;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 发送POST请求
     * @param url
     * @return JSON或者字符串
     * @throws Exception
     */
    public static Object sendPost(String url,Map<String,String> headers,String contentType,Object data) throws Exception{
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            client = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            //处理数据
            if("application/json".equals(contentType)){
                post.setEntity(getByData(data));
            }else if("application/x-www-form-urlencoded".equals(contentType)){
                post.setEntity(getUrlEncodeByData(data));
            }else if("text/plain".equals(contentType)){
                post.setEntity(getTextPlainByData(data));
            }else{

            }
            if(null != headers){
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    post.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
                }
            }
            post.setHeader(new BasicHeader("Content-Type", contentType));
            response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode){
                String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                try{
                    jsonObject = JSONObject.parseObject(result);
                    return jsonObject;
                }catch (Exception e){
                    return  resulMap("fail",e.getMessage());
                }
            }else{
                return  resulMap("fail","请求失败");
            }
        }catch (Exception e){
            return  resulMap("error","请求失败"+e.getMessage());
        }finally {
            response.close();
            client.close();
        }
    }

    public static Map<String,String> resulMap(String status,String msg){
        Map map = new HashMap<>();
        map.put("status",status);
        map.put("msg",msg);
        return map;
    }

    public static Object sendPut(String url,Map<String,String> headers,String contentType,Object data) throws Exception{
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            client = HttpClients.createDefault();
            HttpPut put = new HttpPut(url);
            //处理数据
            if("application/json".equals(contentType)){
                put.setEntity(getByData(data));
            }else if("application/x-www-form-urlencoded".equals(contentType)){
                put.setEntity(getUrlEncodeByData(data));
            }else if("text/plain".equals(contentType)){
                put.setEntity(getTextPlainByData(data));
            }else{

            }
            if(null != headers){
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    put.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
                }
            }
            put.setHeader(new BasicHeader("Content-Type", contentType));
            response = client.execute(put);
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode){
                String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                try{
                    jsonObject = JSONObject.parseObject(result);
                    return jsonObject;
                }catch (Exception e){
                    return  resulMap("fail",e.getMessage());
                }
            }else{
                return  resulMap("fail","请求失败");
            }
        }catch (Exception e){
            return  resulMap("error","请求失败"+e.getMessage());
        }finally {
            response.close();
            client.close();
        }
    }

    public static Object sendPatch(String url,Map<String,String> headers,String contentType,Object data) throws Exception{
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            client = HttpClients.createDefault();
            HttpPatch patch = new HttpPatch(url);
            //处理数据
            if("application/json".equals(contentType)){
                patch.setEntity(getByData(data));
            }else if("application/x-www-form-urlencoded".equals(contentType)){
                patch.setEntity(getUrlEncodeByData(data));
            }else if("text/plain".equals(contentType)){
                patch.setEntity(getTextPlainByData(data));
            }else{

            }
            if(null != headers){
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    patch.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
                }
            }
            patch.setHeader(new BasicHeader("Content-Type", contentType));
            response = client.execute(patch);
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode){
                String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                try{
                    jsonObject = JSONObject.parseObject(result);
                    return jsonObject;
                }catch (Exception e){
                    return  resulMap("fail",e.getMessage());
                }
            }else{
                return  resulMap("fail","请求失败");
            }
        }catch (Exception e){
            return  resulMap("error","请求失败"+e.getMessage());
        }finally {
            response.close();
            client.close();
        }
    }
    /********************************************************************************************************************************************/
    /********************************************************************************************************************************************/
    /********************************************************************************************************************************************/
    /********************************************************************************************************************************************/

    /**
     * MultipartFile 转 File
     * @param multipartFile
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile multipartFile) {
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String filename = originalFilename.substring(0, originalFilename.indexOf("."));
            String type = originalFilename.substring(originalFilename.indexOf("."));
            file=File.createTempFile(filename, type);
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    public static Object sendPost(String url,Map<String,String> headers,String tmppath,Map<String,Object> map,MultiValueMap<String, MultipartFile> files) {
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            client = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            String boundary ="--------------4585696313564699";
            if(null != headers){
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    post.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
                }
            }
            post.setHeader("Content-Type","multipart/form-data; boundary="+boundary);
            //处理数据
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            FileBody fileBody = null;
            for(String key : files.keySet()) {
                fileBody = new FileBody(multipartFileToFile(files.getFirst(key)));
                builder.addPart(key, fileBody);
            }
            for(String key : map.keySet()) {
                builder.addPart(key, new StringBody(map.get(key).toString(), ContentType.create("text/plain", Consts.UTF_8)));
            }
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            //boundary
            builder.setBoundary(boundary);
            post.setEntity(builder.build());
            response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode){
                String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                jsonObject = JSONObject.parseObject(result);
                return jsonObject;
            }else{
                return  resulMap("fail","请求失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return  resulMap("error","请求失败"+e.getMessage());
        }finally {
            try{
                response.close();
                client.close();
            }catch (Exception e){

            }
        }
    }


    public static Object sendPut(String url,Map<String,String> headers,String tmppath,Map<String,Object> map,MultiValueMap<String, MultipartFile> files) throws Exception{
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            client = HttpClients.createDefault();
            HttpPut put = new HttpPut(url);
            String boundary ="--------------4585696313564699";
            if(null != headers){
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    put.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
                }
            }
            put.setHeader("Content-Type","multipart/form-data; boundary="+boundary);
            //处理数据
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            FileBody fileBody = null;
            for(String key : files.keySet()) {
                fileBody = new FileBody(multipartFileToFile(files.getFirst(key)));
                builder.addPart(key, fileBody);
            }
            for(String key : map.keySet()) {
                builder.addPart(key, new StringBody(map.get(key).toString(), ContentType.create("text/plain", Consts.UTF_8)));
            }
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            //boundary
            builder.setBoundary(boundary);
            put.setEntity(builder.build());
            response = client.execute(put);
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode){
                String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                jsonObject = JSONObject.parseObject(result);
                return jsonObject;
            }else{
                return  resulMap("fail","请求失败");
            }
        }catch (Exception e){
            return  resulMap("error","请求失败"+e.getMessage());
        }finally {
            response.close();
            client.close();
        }
    }

    public static Object sendPatch(String url,Map<String,String> headers,String tmppath,Map<String,Object> map,MultiValueMap<String, MultipartFile> files) throws Exception{
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            client = HttpClients.createDefault();
            HttpPatch patch = new HttpPatch(url);
            String boundary ="--------------4585696313564699";
            if(null != headers){
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    patch.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
                }
            }
            patch.setHeader("Content-Type","multipart/form-data; boundary="+boundary);
            //处理数据
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            FileBody fileBody = null;
            for(String key : files.keySet()) {
                fileBody = new FileBody(multipartFileToFile(files.getFirst(key)));
                builder.addPart(key, fileBody);
            }
            for(String key : map.keySet()) {
                builder.addPart(key, new StringBody(map.get(key).toString(), ContentType.create("text/plain", Consts.UTF_8)));
            }
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            //boundary
            builder.setBoundary(boundary);
            response = client.execute(patch);
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode){
                String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                jsonObject = JSONObject.parseObject(result);
                return jsonObject;
            }else{
                return  resulMap("fail","请求失败");
            }
        }catch (Exception e){
            return  resulMap("error","请求失败"+e.getMessage());
        }finally {
            response.close();
            client.close();
        }
    }
}