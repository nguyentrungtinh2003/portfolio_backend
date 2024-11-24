package com.TrungTinhBackend.portfolio_backend.Controller;

import com.TrungTinhBackend.portfolio_backend.Entity.User;
import com.TrungTinhBackend.portfolio_backend.ReqRes.ReqRes;
import com.TrungTinhBackend.portfolio_backend.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ReqRes> register(@RequestPart User user,
                                           @RequestPart MultipartFile img) throws IOException {
        return ResponseEntity.ok(userService.register(user,img));
    }
}
