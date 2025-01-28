//package com.paydrop.config;
//
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.services.gmail.Gmail;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GmailConfig {
//
//    @Bean
//    public Gmail gmail() {
//        HttpTransport httpTransport = new NetHttpTransport();
//        JsonFactory jsonFactory = gmail().getJsonFactory();
//
//        return new Gmail.Builder(httpTransport, jsonFactory, null)
//                .setApplicationName("Paydrop")
//                .build();
//    }
//}