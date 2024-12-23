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

    @Override
    public ReqRes getUserByUsername(String username) {
        ReqRes reqRes = new ReqRes();
        User user = userRepository.findByUsername(username);

        reqRes.setStatusCode(200L);
        reqRes.setMessage("Get user info success !");
        reqRes.setData(user);
        reqRes.setTimestamp(LocalDateTime.now());
        return reqRes;
    }

    @Override
    public ReqRes getUserById(Long id) {

        User user = userRepository.findById(id).orElse(null);

        ReqRes reqRes = new ReqRes();
        reqRes.setStatusCode(200L);
        reqRes.setMessage("Get user by id success !");
        reqRes.setData(user);
        reqRes.setTimestamp(LocalDateTime.now());
        return reqRes;
    }

    @Override
    public ReqRes updateUser(Long id, User user, MultipartFile img) throws IOException {
        ReqRes reqRes = new ReqRes();

        // Lấy thông tin người dùng hiện tại
        ReqRes userResponse = getUserById(id);
        if (userResponse.getData() == null) {
            reqRes.setStatusCode(404L);
            reqRes.setMessage("User not found!");
            reqRes.setTimestamp(LocalDateTime.now());
            return reqRes;
        }

        User existingUser = (User) userResponse.getData();

        // Cập nhật thông tin người dùng nếu có
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
            existingUser.setPhoneNumber(user.getPhoneNumber());
        }
        if (user.getAddress() != null && !user.getAddress().isEmpty()) {
            existingUser.setAddress(user.getAddress());
        }
        if (user.getFullName() != null && !user.getFullName().isEmpty()) {
            existingUser.setFullName(user.getFullName());
        }
        if (user.getPosition() != null && !user.getPosition().isEmpty()) {
            existingUser.setPosition(user.getPosition());
        }
        if (user.getUniversity() != null && !user.getUniversity().isEmpty()) {
            existingUser.setUniversity(user.getUniversity());
        }
        if (user.getBirthDay() != null) {
            existingUser.setBirthDay(user.getBirthDay());
        }
        if (user.getHobby() != null && !user.getHobby().isEmpty()) {
            existingUser.setHobby(user.getHobby());
        }

        // Xử lý ảnh nếu có
        if (img != null && !img.isEmpty()) {
            try {
                // Xóa ảnh cũ trên Cloudinary nếu tồn tại
                if (existingUser.getImg() != null && !existingUser.getImg().isEmpty()) {
                    String oldImgUrl = existingUser.getImg();
                    String publicID = oldImgUrl.substring(oldImgUrl.lastIndexOf("/") + 1, oldImgUrl.lastIndexOf("."));
                    cloudinary.uploader().destroy(publicID, ObjectUtils.emptyMap());
                }

                // Upload ảnh mới lên Cloudinary
                Map uploadResult = cloudinary.uploader().upload(img.getBytes(), ObjectUtils.emptyMap());
                String imgUrl = uploadResult.get("url").toString();
                existingUser.setImg(imgUrl);
            } catch (Exception e) {
                reqRes.setStatusCode(500L);
                reqRes.setMessage("Failed to update image: " + e.getMessage());
                reqRes.setTimestamp(LocalDateTime.now());
                return reqRes;
            }
        }

        // Lưu người dùng đã cập nhật
        userRepository.save(existingUser);

        // Trả về phản hồi thành công
        reqRes.setStatusCode(200L);
        reqRes.setMessage("User updated successfully!");
        reqRes.setData(existingUser);
        reqRes.setTimestamp(LocalDateTime.now());
        return reqRes;
    }

    @Override
    public ReqRes deleteUser(Long id) {

        ReqRes user1 = getUserById(id);

        User user2 = (User) user1.getData();
        userRepository.delete(user2);
        ReqRes reqRes = new ReqRes();
        reqRes.setStatusCode(200L);
        reqRes.setMessage("Delete user by id success !");
        reqRes.setData(user2);
        reqRes.setTimestamp(LocalDateTime.now());
        return reqRes;
    }
}
