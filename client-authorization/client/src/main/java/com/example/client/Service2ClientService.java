package com.example.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Service2ClientService {
    private final RestTemplate template;
    private final OAuth2AuthorizedClientManager manager;

    @Value("${service2.url}")
    String service2Url;

    public Service2ClientService(RestTemplate restTemplate,OAuth2AuthorizedClientManager clientManager){
        this.template=restTemplate;
        this.manager=clientManager;
    }

    public String fetchData(){

//            var authRequest= OAuth2AuthorizeRequest
//                    .withClientRegistrationId("keycloak-client")
//                    .principal("machine")
//                    .build();
//            var client=manager.authorize(authRequest);
//            String token=client.getAccessToken().getTokenValue();
//            HttpHeaders headers=new HttpHeaders();
//            headers.setBearerAuth(token);
//            var res=template.exchange(service2Url+"/data", HttpMethod.GET, new HttpEntity<>(headers), String.class);
//            return res.getBody();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String incomingToken=null;
        if(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken){
            incomingToken=  jwtAuthenticationToken.getToken().getTokenValue();
        }
        HttpHeaders headers=new HttpHeaders();
                    headers.setBearerAuth(incomingToken);
                   var res=template.exchange(service2Url+"/data", HttpMethod.GET, new HttpEntity<>(headers), String.class);
                    return res.getBody();

    }

}
