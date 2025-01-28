//package com.paydrop;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class OAuth2LoginSuccessHandler {
//
//    private final OAuth2AuthorizedClientService clientService;
//
//    public void onAuthenticationSuccess(OAuth2AuthenticationToken authentication) {
//        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
//                authentication.getAuthorizedClientRegistrationId(),
//                authentication.getName());
//
////        getNamelog.debug("OAuth2 login successful for user: {}", authentication.getName());
//    }
//}