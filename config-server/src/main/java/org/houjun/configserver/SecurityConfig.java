package org.houjun.configserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and().httpBasic()
                /**
                 * 如果不禁用csrf，postman 测试接口时，无法登录
                 */
                .and().csrf().disable();// 禁用csrf 跨域攻击保护
    }
}
