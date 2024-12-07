package com.TrungTinhBackend.portfolio_backend.Controller;

import com.TrungTinhBackend.portfolio_backend.Service.Email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public void sendMail(@RequestBody Map<String, String> request) {
        emailService.sendMail(request.get("email"),request.get("subject"),request.get("text"));
    }
}
