package com.TrungTinhBackend.portfolio_backend.Controller;

import com.TrungTinhBackend.portfolio_backend.Entity.User;
import com.TrungTinhBackend.portfolio_backend.ReqRes.ReqRes;
import com.TrungTinhBackend.portfolio_backend.Service.User.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInput;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
        return ResponseEntity.ok(userService.login(user,response));
    }

    @PostMapping("/register")
    public ResponseEntity<ReqRes> register(@RequestPart User user,
                                           @RequestPart MultipartFile img) throws IOException {
        return ResponseEntity.ok(userService.register(user,img));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable Long id,@RequestPart(value = "user",required = false) User user,
                                             @RequestPart(value = "img",required = false) MultipartFile img) throws IOException {
        if (user == null) {
            return ResponseEntity.badRequest()
                    .body(new ReqRes(400L, "Invalid user data!", LocalDateTime.now(), null));
        }
        return ResponseEntity.ok(userService.updateUser(id,user,img));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReqRes> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/byName/{username}")
    public ResponseEntity<ReqRes> getUserByUserName(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReqRes> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

}
