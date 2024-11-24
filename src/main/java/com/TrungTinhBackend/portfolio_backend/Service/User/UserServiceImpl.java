package com.TrungTinhBackend.portfolio_backend.Service.User;

import com.TrungTinhBackend.portfolio_backend.Entity.User;
import com.TrungTinhBackend.portfolio_backend.Repository.UserRepository;
import com.TrungTinhBackend.portfolio_backend.ReqRes.ReqRes;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public ReqRes register(User user, MultipartFile img) throws IOException {
        User user1 = new User();
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
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
