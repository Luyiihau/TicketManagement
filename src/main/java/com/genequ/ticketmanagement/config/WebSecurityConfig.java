package com.genequ.ticketmanagement.config;

import com.genequ.ticketmanagement.service.impl.UserServiceImpl;
import com.genequ.ticketmanagement.web.filter.JWTAuthenticationFilter;
import com.genequ.ticketmanagement.web.filter.JWTLoginFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserServiceImpl customerServiceImpl;


    public WebSecurityConfig(UserServiceImpl customerServiceImpl) {
        this.customerServiceImpl = customerServiceImpl;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()

                .antMatchers("/index", "/user/register","/user/login").permitAll()
                //以 "/admin/" 开头的URL只能让拥有 "ROLE_ADMIN"角色的用户访问。
                .antMatchers("/admin/**").hasRole("ADMIN")

                //任何以"/db/" 开头的URL需要同时具有 "ROLE_ADMIN" 和 "ROLE_DBA"权限的用户才可以访问。
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")

                //任何以"/db/" 开头的URL只需要拥有 "ROLE_ADMIN" 和 "ROLE_DBA"其中一个权限的用户才可以访问。
                .antMatchers("/db/**").hasAnyRole("ADMIN", "DBA")

                //尚未匹配的任何URL都要求用户进行身份验证
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTLoginFilter(authenticationManager()))
                .addFilter(new JWTAuthenticationFilter(authenticationManager()));
//                .logout()
//        //指定登出的url
//                .logoutUrl("/user/logout")
//        //指定登出成功之后跳转的url
//                .logoutSuccessUrl("/index")
//                .permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        //并根据传入的AuthenticationManagerBuilder中的userDetailsService方法来接收我们自定义的认证方法。
        //且该方法必须要实现UserDetailsService这个接口。
//        auth.authenticationProvider(new CustomerAuthenticationProvider(customerServiceImpl));

    }

    @Override
    public void configure(WebSecurity web) {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/static/css/**", "/static/css/fronts/**", "/static/images/**", "/static/js/**", "D:\\TicketManagement\\user\\avatar\\**");
    }
}