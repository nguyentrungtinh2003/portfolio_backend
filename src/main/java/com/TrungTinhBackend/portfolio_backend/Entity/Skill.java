package com.TrungTinhBackend.portfolio_backend.Entity;

import com.TrungTinhBackend.portfolio_backend.Enum.LevelSkill;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String img;
    private LevelSkill level;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
