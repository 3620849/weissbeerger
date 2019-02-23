package com.weissbeerger.demo.configurations;

import com.weissbeerger.demo.configurations.filters.LoginFilter;
import com.weissbeerger.demo.configurations.filters.UserFilter;
import com.weissbeerger.demo.services.TokenAuthService;
import com.weissbeerger.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*to plugin security in project i add
 * 1)in pom spring-boot-starter-security
 * 2)this config class and security work
 * 3)to use secure anatation PreAuthorize i add this @EnableGlobalMethodSecurity(prePostEnabled=true)
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
//it is off automatic security settings
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    TokenAuthService tokenAuthService;
    @Autowired
    CustomAuthManager authenticationManager;
    @Autowired
    UserService userService;
    /**
     * csrf().disable() alow us reach to remote api
     * register filter
     * addFilterBefore(new UserFilter(tokenAuthService), UsernamePasswordAuthenticationFilter.class)
     * next row give access to folowing patterns
     * .antMatchers("/lib/**","/js/**","/getToken","/*").permitAll()
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterBefore(new UserFilter(tokenAuthService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(getLoginFilter(),UserFilter.class)
                .authorizeRequests()

                .antMatchers("/lib/**","/js/**","/api/*","/getToken","/getCurrentUser","/register","/*").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /*Password encoder also used in userService
    */
    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
   /* @Autowired
    public void configureGolobal(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(userService).passwordEncoder(bcryptPasswordEncoder());

    }*/
    private LoginFilter getLoginFilter(){
        return new LoginFilter(new AntPathRequestMatcher("/getToken"),authenticationManager,userService);
    }
}
