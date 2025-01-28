package com.paydrop.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.paydrop.dto.AutomationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GmailService {
    @Autowired(required = true)
    private Gmail gmail;
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    public List<Message> getAllEmails(OAuth2AuthenticationToken authentication) throws IOException {
        // Get credentials

        System.out.println("service");
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                "google",
                authentication.getName()
        );

        String userId = "me"; // special value for authenticated user
        List<Message> messages = new ArrayList<>();

        // Create credential
        GoogleCredential credential = new GoogleCredential()
                .setAccessToken(client.getAccessToken().getTokenValue());

        // Create Gmail service with credentials
        Gmail userGmail = new Gmail.Builder(gmail.getRequestFactory().getTransport(), gmail.getJsonFactory(), credential)
                .setApplicationName("Paydrop")
                .build();

        // Create list request
        Gmail.Users.Messages.List request = userGmail.users().messages().list(userId).setMaxResults(10L);

        // Execute the request and get messages
        ListMessagesResponse response = request.execute();

        if (response.getMessages() != null) {
            for (Message message : response.getMessages()) {
                // Get full message details
                Message fullMessage = userGmail.users().messages()
                        .get(userId, message.getId())
                        .execute();
                messages.add(fullMessage);
            }
        }

        return messages;
    }

    public void addAutomation(AutomationRequest request, OAuth2AuthenticationToken authentication) {
        System.out.println("inside create automation");
    }

    public void createDraft(Authentication authentication) throws IOException {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                "google",
                authentication.getName()
        );

        GoogleCredential credential = new GoogleCredential()
                .setAccessToken(client.getAccessToken().getTokenValue());

        Gmail userGmail = new Gmail.Builder(gmail.getRequestFactory().getTransport(), gmail.getJsonFactory(), credential)
                .setApplicationName("Paydrop")
                .build();

        Message message = new Message();
        message.setRaw("hi this draft ios created");
        Draft draft = new Draft();
        draft.setMessage(message);


        userGmail.users().drafts().create("me", draft).execute();


    }
}