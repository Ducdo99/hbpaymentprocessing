package com.hbpaymentprocessing.hbpaymentprocessing.security;

import com.hbpaymentprocessing.hbpaymentprocessing.filters.LoginFilter;
import com.hbpaymentprocessing.hbpaymentprocessing.filters.RegisterFilter;
import com.hbpaymentprocessing.hbpaymentprocessing.filters.UserFilter;
import com.hbpaymentprocessing.hbpaymentprocessing.services.UserDetailsServiceImpl;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.NoSuchAlgorithmException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private SecurityAuthenticationFilter securityAuthenticationFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private LoginFilter loginFilter;

    @Autowired
    private RegisterFilter registerFilter;

    @Autowired
    private UserFilter userFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        // Do not create HttpSession, do not also use to contain data
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // To allow any request to send web application
        http.authorizeRequests().antMatchers("/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);

        // Filter ordering is LoginFilter -> RegisterFilter -> UserFilter -> SecurityAuthenticationFilter
        http.addFilterBefore(securityAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(userFilter, SecurityAuthenticationFilter.class);
        http.addFilterBefore(registerFilter, UserFilter.class);
        http.addFilterBefore(loginFilter, RegisterFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() throws NoSuchAlgorithmException {
        return new BCryptPasswordEncoder();
    }
}
