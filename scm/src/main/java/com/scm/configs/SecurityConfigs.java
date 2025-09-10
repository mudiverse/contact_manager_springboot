package com.scm.configs;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.scm.services.impl.SecurityCustomUserDetailsService;

//spring security se related configurations
@Configuration
public class SecurityConfigs {

    
    @Autowired
    private SecurityCustomUserDetailsService userDetailsService;
    
    @SuppressWarnings("deprecation")
    @Bean
    public AuthenticationProvider authenticationProvider(){
       DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       //user details service ka object
        provider.setUserDetailsService(userDetailsService);
        //password encoder ka object
        provider.setPasswordEncoder(passwordEncoder());

       return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
