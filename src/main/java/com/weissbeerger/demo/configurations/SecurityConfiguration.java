package com.weissbeerger.demo.configurations;

import com.weissbeerger.demo.configurations.filters.LoginFilter;
import com.weissbeerger.demo.configurations.providers.AnonymousProvider;
import com.weissbeerger.demo.configurations.providers.BasicAuthenticationProvider;
import com.weissbeerger.demo.configurations.providers.TokenAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    BasicAuthenticationProvider basicAuthProvider;
    @Autowired
    TokenAuthenticationProvider tokenAuthProvider;
    @Autowired
    AnonymousProvider anonymousProvider;

    @Override
    public void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        //pay attention order is important
        auth.authenticationProvider(tokenAuthProvider);
        auth.authenticationProvider(basicAuthProvider);
        auth.authenticationProvider(anonymousProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/api/**","/getCurrentUser","/register").authenticated()
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(getLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    public LoginFilter getLoginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter(new OrRequestMatcher(
                new AntPathRequestMatcher("/api/**"),
                new AntPathRequestMatcher("/getCurrentUser"),
                new AntPathRequestMatcher("/register")));
        loginFilter.setAuthenticationManager(this.authenticationManager());
        return loginFilter;
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}