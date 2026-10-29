package com.project.google;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String login(OAuth2AuthenticationToken token){
        return "Welcome,"+token.getName()+" "+token.getPrincipal().getAttribute("email");
    }
}
