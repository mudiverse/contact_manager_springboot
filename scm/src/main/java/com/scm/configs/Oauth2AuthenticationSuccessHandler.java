package com.scm.configs;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = org.slf4j.LoggerFactory.getLogger(Oauth2AuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.info("OAuth2 login successful for user: ");

        // identify which provider is used for login
        var oauth2AuthToken = (OAuth2AuthenticationToken) authentication;
        String providerName = oauth2AuthToken.getAuthorizedClientRegistrationId();
        logger.info("Login provider: " + providerName);

        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        oauthUser.getAttributes().forEach((k, v) -> {
            logger.info(k + " : " + v);
        });

        // yaha se hame pata chal gaya ki kon se provider se login hua hai
        User user = new User();
        user.setUserid(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEnabled(true);
        user.setEmailVerified(true);

        if (providerName.equalsIgnoreCase("google")) {
            // agar google se login hua hai to
            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setProfilePic(oauthUser.getAttribute("picture").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProviderId(oauthUser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setPassword("dummygooglepassword");
            user.setAbout("Hello, I am using Google OAuth2 for authentication.");

        } else if (providerName.equalsIgnoreCase("github")) {
            // agar github se login hua hai to
            String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                    : oauthUser.getAttribute("login").toString() + "@gmail.com";
            String picture = oauthUser.getAttribute("avatar_url").toString();
            String name = oauthUser.getAttribute("login").toString();
            String providerid = oauthUser.getName();
            

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderId(providerid);
            user.setProvider(Providers.GITHUB);
            user.setPassword("dummygithubpassword");
            user.setAbout("Hello, I am using GitHub OAuth2 for authentication.");

        } else {
            // agr koi or provider se login hua hai to
            logger.info("Login provider not supported: " + providerName);
        }

        // DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
        // //data leke google se database me store karna hai
        // String email = user.getAttribute("email").toString();
        // String name = user.getAttribute("name").toString();
        // String pic = user.getAttribute("picture").toString();

        // //save in database
        // User user2 = new User();
        // user2.setEmail(email);
        // user2.setName(name);
        // user2.setProfilePic("call.png");
        // user2.setPassword("passwordoauth2");
        // user2.setEnabled(true);
        // user2.setUserid(UUID.randomUUID().toString());
        // user2.setProvider(Providers.GOOGLE);
        // user2.setEmailVerified(true);
        // user2.setProviderId(user.getName());
        // user2.setRoleList(List.of(AppConstants.ROLE_USER));

        // database me save karna hai
        // agar db me hai to no save else save
        User user3 = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (user3 == null) {
            userRepo.save(user);
            logger.info("New user registered with email: " + user.getEmail());
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");

    }

}
