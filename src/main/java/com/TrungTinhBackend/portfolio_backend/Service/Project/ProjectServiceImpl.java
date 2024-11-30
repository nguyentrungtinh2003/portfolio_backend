package com.TrungTinhBackend.portfolio_backend.Service.Project;

import com.TrungTinhBackend.portfolio_backend.Entity.Project;
import com.TrungTinhBackend.portfolio_backend.Entity.Skill;
import com.TrungTinhBackend.portfolio_backend.Entity.User;
import com.TrungTinhBackend.portfolio_backend.Repository.ProjectRepository;
import com.TrungTinhBackend.portfolio_backend.Repository.SkillRepository;
import com.TrungTinhBackend.portfolio_backend.Repository.UserRepository;
import com.TrungTinhBackend.portfolio_backend.ReqRes.ReqRes;
import com.TrungTinhBackend.portfolio_backend.Service.Skill.SkillService;
import com.TrungTinhBackend.portfolio_backend.Service.User.UserService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SkillService skillService;

    @Autowired
    private UserService userService;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public ReqRes addProject(Project project, MultipartFile img) throws IOException {

        ReqRes user = new ReqRes();
        if(project.getUser() != null && project.getUser().getId() != null) {
             user = userService.getUserById(project.getUser().getId());
        }else {
             user = userService.getUserById(2L);
        }

        ReqRes skill = skillService.getAllSkill();

        List<Skill> skillList = (List<Skill>) skill.getData();

        Project project1 = new Project();
        if (img != null && !img.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(img.getBytes(), ObjectUtils.emptyMap());
            String imgUrl = uploadResult.get("url").toString();
            project1.setImg(imgUrl); // Lưu URL của ảnh
        }
        project1.setName(project.getName());
        project1.setDescription(project.getDescription());
        project1.setEndDate(project.getEndDate());
        project1.setStartDate(project.getStartDate());
        project1.setStatus(project.getStatus());
        project1.setTechnology(project.getTechnology());
        project1.setGithubLink(project.getGithubLink());
        project1.setUser((User) user.getData());
        project1.setSkills(skillList);

        projectRepository.save(project1);

        ReqRes reqRes = new ReqRes();
        reqRes.setStatusCode(200L);
        reqRes.setMessage("Add project success !");
        reqRes.setData(project1);
        reqRes.setTimestamp(LocalDateTime.now());
        return reqRes;
    }

    @Override
    public ReqRes getAllProject() {

        List<Project> projects = projectRepository.findAll();
        ReqRes reqRes = new ReqRes();
        reqRes.setStatusCode(200L);
        reqRes.setMessage("Get all project success !");
        reqRes.setData(projects);
        reqRes.setTimestamp(LocalDateTime.now());
        return reqRes;
    }
}