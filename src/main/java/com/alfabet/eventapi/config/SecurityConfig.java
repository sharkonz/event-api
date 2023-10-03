//package com.alfabet.eventapi.config;
//
//import org.springframework.context.annotation.Configuration;
//
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//            .antMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**")
//            .permitAll()  // Allow everyone to access Swagger UI and API docs
//            .anyRequest().authenticated()  // Any other request should be authenticated
//            .and()
//            .csrf().disable();  // Depending on your requirements, you might want to disable CSRF. Do this with caution.
//    }
//}
