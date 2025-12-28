package com.scm.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.services.impl.SecurityCustomUserDetailsService;

//spring security se related configurations
@Configuration
public class SecurityConfigs {

    
    @Autowired
    private SecurityCustomUserDetailsService userDetailsService;
    @Autowired
    private Oauth2AuthenticationSuccessHandler handler;
    
    //cofig od authentication provider
    @SuppressWarnings("deprecation")
    public DaoAuthenticationProvider authenticationProvider(){
       DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       //user details service ka object
        provider.setUserDetailsService(userDetailsService);
        //password encoder ka object
        provider.setPasswordEncoder(passwordEncoder());

       return provider;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
       
        //configuration of urls that wihch are public/private
        httpSecurity.authorizeHttpRequests(auth->{
            // auth.requestMatchers("/home").permitAll();
            auth.requestMatchers("/user/**").authenticated();//means agar user /user/** ye url hit karega to uske liye authentication zaruri hai

            auth.anyRequest().permitAll();
        });
    
        //form default login
        //agar kuch change karna hua to ham yaha aanege and :form login ka config karenge
        httpSecurity.formLogin(formLogin->{
            //apna login page
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/profile");
            // formLogin.failureForwardUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
        });

        //logout configuration
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logout->{
            logout.logoutUrl("/do-logout");
            logout.logoutSuccessUrl("/login?logout=true");
        });

        //oauth2 login configuration
        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });


        return httpSecurity.build();
        
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
