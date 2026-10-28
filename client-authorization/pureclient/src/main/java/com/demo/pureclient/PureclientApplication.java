package com.demo.pureclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.client.RestTemplate;



@SpringBootApplication
public class PureclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PureclientApplication.class, args);
	}
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Bean
    public OAuth2AuthorizedClientService auth2AuthorizedClientService(ClientRegistrationRepository repository){
        return new InMemoryOAuth2AuthorizedClientService(repository);
    }

    @Bean
    public OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager(
            ClientRegistrationRepository repository,
            OAuth2AuthorizedClientService clientService){
    var manager=new AuthorizedClientServiceOAuth2AuthorizedClientManager(repository,clientService);
    OAuth2AuthorizedClientProvider provider= OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();
    manager.setAuthorizedClientProvider(provider);
    return manager;
    }

    @Bean
    public CommandLineRunner runner(
            OAuth2AuthorizedClientManager manager,
            RestTemplate template, @Value("${service2.url}")String service2Url){
        return (args)->{
            var authRequest= OAuth2AuthorizeRequest
                .withClientRegistrationId("keycloak-client")
                .principal("machine")
                .build();
        var client=manager.authorize(authRequest);
        String token=client.getAccessToken().getTokenValue();
        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(token);
        var res=template.exchange(service2Url+"/data", HttpMethod.GET,new HttpEntity<>(headers),String.class);
        System.out.println("Response from service2 :"+res);

    };}
}
