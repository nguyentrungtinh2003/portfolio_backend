package com.TrungTinhBackend.portfolio_backend.Service.Skill;

import com.TrungTinhBackend.portfolio_backend.Entity.Skill;
import com.TrungTinhBackend.portfolio_backend.Entity.User;
import com.TrungTinhBackend.portfolio_backend.Repository.SkillRepository;
import com.TrungTinhBackend.portfolio_backend.Repository.UserRepository;
import com.TrungTinhBackend.portfolio_backend.ReqRes.ReqRes;
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
public class SkillServiceImpl implements SkillService{

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public ReqRes addSkill(Skill skill, MultipartFile img) throws IOException {

        ReqRes user = userService.getUserById(skill.getUser().getId());

        Skill skill1 = new Skill();
        skill1.setName(skill.getName());
        skill1.setLevel(skill.getLevel());
        skill1.setUser((User) user.getData());

        if (img != null && !img.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(img.getBytes(), ObjectUtils.emptyMap());
            String imgUrl = uploadResult.get("url").toString();
            skill1.setImg(imgUrl); // Lưu URL của ảnh
        }

        skillRepository.save(skill1);

        ReqRes reqRes = new ReqRes();
        reqRes.setStatusCode(200L);
        reqRes.setMessage("Add skill success !");
        reqRes.setData(skill1);
        reqRes.setTimestamp(LocalDateTime.now());
        return reqRes;
    }

    @Override
    public ReqRes getAllSkill() {

        List<Skill> skills = skillRepository.findAll();

        ReqRes reqRes = new ReqRes();
        reqRes.setStatusCode(200L);
        reqRes.setMessage("Get all skill success !");
        reqRes.setData(skills);
        reqRes.setTimestamp(LocalDateTime.now());
        return reqRes;
    }

    @Override
    public ReqRes getSkillById(Long id) {

        Skill skill = skillRepository.findById(id).orElse(null);

        ReqRes reqRes = new ReqRes();
        reqRes.setStatusCode(200L);
        reqRes.setMessage("Get skill by id success !");
        reqRes.setData(skill);
        reqRes.setTimestamp(LocalDateTime.now());
        return reqRes;
    }

    @Override
    public ReqRes updateSkill(Long id, Skill skill, MultipartFile img) throws IOException {

        ReqRes user = new ReqRes();
        if (skill.getUser() == null) {
            user = userService.getUserById(2L);
        }else {
            user = userService.getUserById(skill.getUser().getId());
        }

        User user1 = (User) user.getData();
        ReqRes skill1 = getSkillById(id);
        Skill skill2 = (Skill) skill1.getData();
        skill2.setName(skill.getName());
        skill2.setLevel(skill.getLevel());
        skill2.setUser(user1);

        if (img != null && !img.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(img.getBytes(), ObjectUtils.emptyMap());
            String imgUrl = uploadResult.get("url").toString();
            skill2.setImg(imgUrl); // Lưu URL của ảnh
        }

        skillRepository.save(skill2);

        ReqRes reqRes = new ReqRes();
        reqRes.setStatusCode(200L);
        reqRes.setMessage("Update skill success !");
        reqRes.setData(skill2);
        reqRes.setTimestamp(LocalDateTime.now());
        return reqRes;
    }

    @Override
    public ReqRes deleteSkill(Long id) {
        ReqRes skill = getSkillById(id);
        Skill skill1 = (Skill) skill.getData();

        skillRepository.delete(skill1);
        ReqRes reqRes = new ReqRes();
        reqRes.setStatusCode(200L);
        reqRes.setMessage("Delete skill success !");
        reqRes.setData(skill1);
        reqRes.setTimestamp(LocalDateTime.now());
        return reqRes;
    }
}
