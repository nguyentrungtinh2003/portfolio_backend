package com.TrungTinhBackend.portfolio_backend.Controller;

import com.TrungTinhBackend.portfolio_backend.Entity.Skill;
import com.TrungTinhBackend.portfolio_backend.ReqRes.ReqRes;
import com.TrungTinhBackend.portfolio_backend.Service.Skill.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/skill")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping("/add")
    public ResponseEntity<ReqRes> addSkill(@RequestPart Skill skill,
                                           @RequestPart MultipartFile img) throws IOException {
        return ResponseEntity.ok(skillService.addSkill(skill,img));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReqRes> updateSkill(@PathVariable Long id,@RequestPart(value = "skill",required = false) Skill skill,
                                           @RequestPart(value = "img",required = false) MultipartFile img) throws IOException {
        return ResponseEntity.ok(skillService.updateSkill(id,skill,img));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReqRes> deleteSkill(@PathVariable Long id) {
        return ResponseEntity.ok(skillService.deleteSkill(id));
    }

    @GetMapping("/all")
    public ResponseEntity<ReqRes> getAllSkill() {
        return ResponseEntity.ok(skillService.getAllSkill());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReqRes> getSkillById(@PathVariable Long id) {
        return ResponseEntity.ok(skillService.getSkillById(id));
    }
}
