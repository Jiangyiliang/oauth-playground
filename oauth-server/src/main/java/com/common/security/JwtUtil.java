package com.common.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * token的管理
 * @author ZWP
 * 2017年10月25日
 */
@Component
public class JwtUtil {

    private final static Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    /** token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj */
    public static final String SECRET = "JKKLJOoasdlfj";
    /** token 过期时间: 10天 */
    public static final int calendarField = Calendar.DATE;
    public static final int calendarInterval = 2;

    public static HttpServletRequest getRequest(){
        try{
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }catch(Exception e){
            return null;
        }
    }

    /**
     * 创建jwt
     * @param id       用户id
     * @param username 或者json字符串
     * @return
     * @throws Exception
     */
    public static String createToken(Integer id, String username) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        Date now = new Date(nowMillis);
        Map<String,Object> claims = new HashMap<String,Object>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        claims.put("userId", id);
        claims.put("username", username);
        SecretKey key = generalKey();//生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        Calendar nowTime = Calendar.getInstance() ;
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();
        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId("jwt")                  //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)           //iat: jwt的签发时间
                .setSubject(username)        //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setExpiration(expiresDate)
                .signWith(signatureAlgorithm, key);//设置签名使用的签名算法和签名使用的秘钥
        return builder.compact();
    }

    /**
     * 创建jwt
     * @param id       用户id
     * @param username 或者json字符串
     * @return
     * @throws Exception
     */
    public static String createToken(Integer id, String username,String orgIds,String distrinctIds,String userType) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        Date now = new Date(nowMillis);
        Map<String,Object> claims = new HashMap<String,Object>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        claims.put("userId", id);
        claims.put("username", username);
        claims.put("orgIds", orgIds);
        claims.put("distrinctIds", distrinctIds);
        claims.put("userType", userType);
        SecretKey key = generalKey();//生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。

        Calendar nowTime = Calendar.getInstance() ;
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();
        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId("jwt")                  //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)           //iat: jwt的签发时间
                .setSubject(username)        //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setExpiration(expiresDate)
                .signWith(signatureAlgorithm, key);//设置签名使用的签名算法和签名使用的秘钥
        return builder.compact();
    }


    /**
     * 由字符串生成加密key
     * @return
     */
    public static SecretKey generalKey(){
        String stringKey = SECRET;//本地配置文件中加密的密文7786df7fc3a34e26a61c034d5ec8245d
        byte[] encodedKey = Base64.decodeBase64(stringKey);//本地的密码解码[B@152f6e2
        System.out.println(encodedKey);//[B@152f6e2
        System.out.println(Base64.encodeBase64URLSafeString(encodedKey));//7786df7fc3a34e26a61c034d5ec8245d
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");// 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。（后面的文章中马上回推出讲解Java加密和解密的一些算法）
        return key;
    }

    /**
     * 解密jwt
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception{
        SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
        Claims claims = Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(key)         //设置签名的秘钥
                .parseClaimsJws(jwt).getBody();//设置需要解析的jwt
        return claims;
    }


    /**
     * @Title: getUserId @Description:获取token中的用户id @param
     * httpServletRequest @param httpServletResponse @return @throws
     */
    public static String getToken() {
        try {
            HttpServletRequest httpServletRequest = JwtUtil.getRequest();
            String token = httpServletRequest.getHeader("x-access-token");
            return token;
        } catch (Exception e) {
            logger.error("获取token失败"+ e.getMessage() );
            return null;
        }
    }

    public static Integer getUserId() {
        HttpServletRequest httpServletRequest = JwtUtil.getRequest();
        String token = httpServletRequest.getHeader("x-access-token");
        try {
            Claims c = JwtUtil.parseJWT(token);
            return c.get("userId", Integer.class);
        } catch (Exception e) {
            logger.error("token解析失败"+ e.getMessage() );
            return 0;
        }
    }

//    public static Integer getUserId(String token) {
//        if(StringUtils.isEmpty(token)) return  0;
//        try {
//            Claims c = JwtUtil.parseJWT(token);
//            return c.get("userId", Integer.class);
//        } catch (Exception e) {
//            logger.error("token解析失败"+ e.getMessage() );
//            return 0;
//        }
//    }

    /**
     * @Title: getUserId @Description:获取token中的用户id @param
     * httpServletRequest @param httpServletResponse @return @throws
     */
    public static String getUserName() {
        HttpServletRequest httpServletRequest = JwtUtil.getRequest();
        String token = httpServletRequest.getHeader("x-access-token");
        try {
            Claims c = JwtUtil.parseJWT(token);
            return c.get("username", String.class);
        } catch (Exception e) {
            logger.error("token解析失败"+ e.getMessage() );
            return "";
        }
    }

    /**
     * @Title: getUserId @Description:获取token中的用户id @param
     * httpServletRequest @param httpServletResponse @return @throws
     */
    public static Map<String,Object> getUserInfo() {
        HttpServletRequest httpServletRequest = JwtUtil.getRequest();
        String token = httpServletRequest.getHeader("x-access-token");
        try {
            Claims c = JwtUtil.parseJWT(token);
            Map resultMap = new HashMap();
            resultMap.put("userId",c.get("userId", Integer.class));
            resultMap.put("username",c.get("username", String.class));
            resultMap.put("orgIds",c.get("orgIds", String.class));
            resultMap.put("distrinctIds",c.get("distrinctIds", String.class));
            resultMap.put("userType",c.get("userType", String.class));
            return resultMap;
        } catch (Exception e) {
            logger.error("token解析失败"+ e.getMessage() );
            return null;
        }
    }



    public static void main(String args[]) throws Exception{
        JwtUtil util=   new JwtUtil();
        String ab=util.createToken(1, "eric");
        System.out.println(ab);
        Claims c=util.parseJWT(ab);//注意：如果jwt已经过期了，这里会抛出jwt过期异常。
        System.out.println(c.getId());//jwt
        System.out.println(c.getIssuedAt());//Mon Feb 05 20:50:49 CST 2018
        System.out.println(c.getSubject());//{id:100,name:xiaohong}
        System.out.println(c.getIssuer());//null
        System.out.println(c.get("userId", Integer.class));//DSSFAWDWADAS...
        System.out.println(c.get("username", String.class));//DSSFAWDWADAS...
    }

}