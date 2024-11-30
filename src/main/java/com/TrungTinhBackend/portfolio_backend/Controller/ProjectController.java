package com.TrungTinhBackend.portfolio_backend.Controller;

import com.TrungTinhBackend.portfolio_backend.Entity.Project;
import com.TrungTinhBackend.portfolio_backend.Entity.Skill;
import com.TrungTinhBackend.portfolio_backend.ReqRes.ReqRes;
import com.TrungTinhBackend.portfolio_backend.Service.Project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/add")
    public ResponseEntity<ReqRes> addProject(@RequestPart Project project,
                                           @RequestPart MultipartFile img) throws IOException {
        return ResponseEntity.ok(projectService.addProject(project,img));
    }

    @GetMapping("/all")
    public ResponseEntity<ReqRes> getAllProject() {
        return ResponseEntity.ok(projectService.getAllProject());
    }
}
