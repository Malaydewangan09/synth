package com.paydrop;

import com.google.api.services.gmail.model.Message;
import com.paydrop.entity.AutomationRequest;
import com.paydrop.service.GmailService;
import org.springframework.ai.ollama.OllamaChatModel;
import com.google.api.services.gmail.model.MessagePart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@CrossOrigin(
        origins = "http://localhost:3000",
        allowedHeaders = {"Content-Type", "Accept", "X-XSRF-TOKEN"},
        allowCredentials = "true",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS}
)
public class AuthController {
    @Autowired
    private GmailService gmailService;
    @Autowired
    private OllamaChatModel chatModel;
    private static List<Message> emails;
    private static final HashMap<Integer, String> context = new HashMap<>() ;


    // make a api to get user details from authenticaltion i want only email
    @GetMapping("/auth/user")
    public ResponseEntity<?> getUser(Authentication authentication) {
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User not authenticated with OAuth2");
        }
        try {
            AtomicReference<Object> userEmail = new AtomicReference<>();
            Collection<?> authorities = authentication.getAuthorities();
            authorities.stream().findFirst().ifPresent(authority -> {
                OAuth2UserAuthority oAuth2UserAuthority = (OAuth2UserAuthority) authority;
                Object email = oAuth2UserAuthority.getAttributes().get("email");
                userEmail.set(email);
            });

            return ResponseEntity.ok(userEmail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error");
        }
    }

    @GetMapping("/auth/emails")
    public ResponseEntity<?> dashboard(Authentication authentication) {
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User not authenticated with OAuth2");
        }

        try {
            emails = gmailService.getAllEmails((OAuth2AuthenticationToken) authentication);
            emails.stream().forEach(email -> {
                StringBuilder content = new StringBuilder();
                if (email.getPayload().getParts()==null){
                    content.append(new String(
                            Base64.getDecoder().decode(email.getPayload().getBody().getData().replace('-', '+')
                                    .replace('_', '/')),StandardCharsets.UTF_8));

                }
                    else {
                        try {
                            email.getPayload().getParts().forEach(messagePart -> {
                                content.append(new String(
                                        Base64.getDecoder().decode(messagePart.getBody().getData().replace('-', '+')
                                                .replace('_', '/')),StandardCharsets.UTF_8));

                            });
                        } catch (Exception e) {
                            System.out.println("Error decoding email content"+e.getMessage());
                        }
                }

                    context.put(context.size()+1,content.toString());

            });
            return ResponseEntity.ok(emails);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching emails: " + e.getMessage());
        }

    }

    @GetMapping("/ai")
    public ResponseEntity<?> web(@RequestBody String message) throws IOException {
        // Call the chat model
        String response = chatModel.call(message);
        return ResponseEntity.ok(response);
    }

    private String decodeBase64Email(String encodedData) {
        if (encodedData == null) {
            return "";
        }

        // Replace URL-safe characters with standard Base64 characters
        String standardBase64 = encodedData
                .replace('-', '+')
                .replace('_', '/')
                .trim();

        try {
            // Pad the Base64 string if needed
            while (standardBase64.length() % 4 != 0) {
                standardBase64 += "=";
            }

            // Decode using Java's Base64 decoder
            byte[] decodedBytes = Base64.getDecoder().decode(standardBase64);
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

    private List<String> extractEmailContents(List<Message> emails, String id) {
        List<String> emailContents = new ArrayList<>();

        for (Message message : emails) {

            if (message.getId().equals(id)) {
                try {
                    if (message.getPayload() == null) {
                        continue;
                    }

                    // Handle multipart messages
                    if (message.getPayload().getParts() != null) {
                        for (MessagePart part : message.getPayload().getParts()) {
                            if ("text/plain".equalsIgnoreCase(part.getMimeType())) {
                                String decodedContent = decodeBase64Email(part.getBody().getData());
                                if (!decodedContent.isEmpty()) {
                                    emailContents.add(decodedContent);
                                }
                            }
                        }
                    }
                    // Handle single part messages
                    else if ("text/plain".equalsIgnoreCase(message.getPayload().getMimeType())) {
                        String decodedContent = decodeBase64Email(message.getPayload().getBody().getData());
                        if (!decodedContent.isEmpty()) {
                            emailContents.add(decodedContent);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error extracting email content: " + e.getMessage());
                }
            }
        }

        return emailContents;
    }


    @PostMapping("/auth/automations")
    public ResponseEntity<?> createAutomation(@RequestBody AutomationRequest request, Authentication authentication) {
        System.out.println("inside auth");
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            System.out.println("not auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User not authenticated with OAuth2");
        }

        try {
            // Add validation
            gmailService.addAutomation(request, (OAuth2AuthenticationToken) authentication);
            return ResponseEntity.ok(Map.of("status", "success", "message", "Email sent successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    // ... existing code ...

    @PostMapping("/auth/create-draft")
    public ResponseEntity<?> createDraft(Authentication authentication) {
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("status", "error", "message", "User not authenticated with OAuth2"));
        }

        try {
            gmailService.createDraft((OAuth2AuthenticationToken) authentication);
            return ResponseEntity.ok(Map.of("status", "success", "message", "Draft created successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

// ... existing code ...
}
