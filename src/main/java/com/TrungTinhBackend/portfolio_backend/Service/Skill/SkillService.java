package com.TrungTinhBackend.portfolio_backend.Service.Skill;

import com.TrungTinhBackend.portfolio_backend.Entity.Skill;
import com.TrungTinhBackend.portfolio_backend.ReqRes.ReqRes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SkillService {
    public ReqRes addSkill(Skill skill, MultipartFile img) throws IOException;

    public ReqRes getAllSkill();

    public ReqRes getSkillById(Long id);

    public ReqRes updateSkill(Long id, Skill skill, MultipartFile img) throws IOException;

    public ReqRes deleteSkill(Long id);
}
