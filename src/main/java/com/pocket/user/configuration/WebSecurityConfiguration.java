package com.app.pocket.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private UserDetailsService jwtService;
    //returns AuthenticationManagerBean
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        System.out.println("Inside WebSecurityConfiguration.authenticationManagerBean");
        return super.authenticationManagerBean();
    }

    /**
     *This methods will be executed on start up to set up HttpSecurity or configuring spring security filters.
     * basically we define the authorization rules here
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("Inside WebSecurityConfiguration.configure");
       http.cors();
       http.csrf().disable()
               .authorizeRequests().antMatchers("/authenticate", "/registerNewUser","/api/v1/users/createRole","/swagger-ui.html").permitAll()
               .antMatchers().permitAll()
               .anyRequest().authenticated()
               .and()
               .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
               .and()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               ;
        /**
         *We are configuring our custom filter "JwtRequestFilter" here
         */
       http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
       
    }

    /**
     *For password encoding
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        System.out.println("Inside WebSecurityConfiguration.passwordEncoder");
        return new BCryptPasswordEncoder();
    }

    /**
     *Configuring AuthenticationManagerBuilder, setting up authentication object basically
     * We also specified which service we are going to use, there are many like in-memory, userDetails etc.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        System.out.println("Inside WebSecurityConfiguration.configureGlobal");
        authenticationManagerBuilder.userDetailsService(jwtService).passwordEncoder(passwordEncoder());
    }
}
