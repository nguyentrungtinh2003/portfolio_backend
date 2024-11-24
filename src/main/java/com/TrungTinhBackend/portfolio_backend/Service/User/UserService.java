package com.TrungTinhBackend.portfolio_backend.Service.User;

import com.TrungTinhBackend.portfolio_backend.Entity.User;
import com.TrungTinhBackend.portfolio_backend.ReqRes.ReqRes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    public ReqRes register(User user, MultipartFile img) throws IOException;
}
