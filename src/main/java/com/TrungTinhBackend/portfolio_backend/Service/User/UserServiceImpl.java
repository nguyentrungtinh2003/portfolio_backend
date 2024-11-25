package com.TrungTinhBackend.portfolio_backend.Service.User;

import com.TrungTinhBackend.portfolio_backend.Entity.User;
import com.TrungTinhBackend.portfolio_backend.Repository.UserRepository;
import com.TrungTinhBackend.portfolio_backend.ReqRes.ReqRes;
import com.TrungTinhBackend.portfolio_backend.Service.Jwt.JwtUtils;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ReqRes login(User user, HttpServletResponse response) {
       ReqRes reqRes = new ReqRes();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            var user2 = userRepository.findByUsername(user.getUsername());
            if (user2 == null) {
                throw new BadCredentialsException("User not found");
            }

            var jwt = jwtUtils.generateToken(user2);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user2);

            // Tạo cookie chứa JWT token
            Cookie jwtCookie = new Cookie("authToken", jwt);
            jwtCookie.setHttpOnly(true); // Cookie không thể truy cập từ JavaScript để bảo mật
            jwtCookie.setMaxAge(24 * 60 * 60); // Cookie hết hạn sau 24 giờ
            jwtCookie.setPath("/"); // Có hiệu lực trên toàn bộ ứng dụng
            response.addCookie(jwtCookie); // Thêm cookie vào phản hồi

            reqRes.setStatusCode(200L);
            reqRes.setMessage("User login success !");
            reqRes.setToken(jwt);
            reqRes.setData(user2);
            reqRes.setTimestamp(LocalDateTime.now());

            return reqRes;

        } catch (BadCredentialsException e) {
            reqRes.setStatusCode(403L);
            reqRes.setMessage("Invalid credentials");
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500L);
            reqRes.setMessage(e.getMessage());
            return reqRes;
        }
    }

    @Override
    public ReqRes register(User user, MultipartFile img) throws IOException {
        User user1 = new User();
        user1.setUsername(user.getUsername());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setEmail(user.getEmail());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setAddress(user.getAddress());
        user1.setFullName(user.getFullName());
        user1.setPosition(user.getPosition());
        user1.setUniversity(user.getUniversity());
        user1.setBirthDay(user.getBirthDay());
        user1.setHobby(user.getHobby());

        if (img != null && !img.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(img.getBytes(), ObjectUtils.emptyMap());
            String imgUrl = uploadResult.get("url").toString();
            user1.setImg(imgUrl); // Lưu URL của ảnh
        }

        userRepository.save(user1);

        ReqRes reqRes = new ReqRes();
        reqRes.setStatusCode(200L);
        reqRes.setMessage("Register success !");
        reqRes.setData(user1);
        reqRes.setTimestamp(LocalDateTime.now());

        return reqRes;

    }
}
