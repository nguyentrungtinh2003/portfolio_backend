package com.TrungTinhBackend.portfolio_backend.Service.User;

import com.TrungTinhBackend.portfolio_backend.Entity.User;
import com.TrungTinhBackend.portfolio_backend.ReqRes.ReqRes;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    public ReqRes login(User user, HttpServletResponse response);
    public ReqRes register(User user, MultipartFile img) throws IOException;

    public ReqRes getUserByUsername(String username);

    public ReqRes getUserById(Long id);
}
