package com.common.fiter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import java.util.Objects;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //校验用户
        auth.userDetailsService(userService).passwordEncoder( new PasswordEncoder() {
            //对密码进行加密
            @Override
            public String encode(CharSequence charSequence) {
                System.out.println(charSequence.toString());
                return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
            }
            //对密码进行判断匹配
            @Override
            public boolean matches(CharSequence charSequence, String s) {
                String encode = DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
                boolean res = s.equals( encode );
                return res;
            }
        } );

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.requestMatchers()
                .antMatchers("/oauth/**","/login","/login-error")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .and()
                .formLogin().loginPage("/login").failureUrl("/login-error");
//        http
//                .authorizeRequests()//定义哪些url需要被保护  哪些不需要保护
//                .antMatchers("/oauth/token" , "oauth/check_token").permitAll()//定义这两个链接不需要登录可访问
//                .antMatchers("/**").permitAll() //定义所有的都不需要登录  目前是测试需要
//                .anyRequest().authenticated() //其他的都需要登录
//                //.antMatchers("/sys/**").hasRole("admin")///sys/**下的请求 需要有admin的角色
//                .and()
//                .formLogin().loginPage("/login")//如果未登录则跳转登录的页面   这儿可以控制登录成功和登录失败跳转的页面
//                .usernameParameter("username").passwordParameter("password").permitAll()//定义号码与密码的parameter
//                .and()
//                .csrf().disable();//防止跨站请求  spring security中默认开启
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return Objects.equals(charSequence.toString(),s);
            }
        };
    }


}
