package com.TrungTinhBackend.portfolio_backend.Service.Project;

import com.TrungTinhBackend.portfolio_backend.Entity.Project;
import com.TrungTinhBackend.portfolio_backend.ReqRes.ReqRes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProjectService {
    public ReqRes addProject(Project project, MultipartFile img) throws IOException;

    public ReqRes getAllProject();

    public ReqRes getProjectById(Long id);

    public ReqRes updateProject(Long id, Project project, MultipartFile img) throws IOException;

    public ReqRes deleteProject(Long id);
}
