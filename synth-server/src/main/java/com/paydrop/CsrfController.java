package com.paydrop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CsrfController {

    @GetMapping("/csrf")
    public void getCsrfToken() {
        // The token will be automatically included in the response
    }
}