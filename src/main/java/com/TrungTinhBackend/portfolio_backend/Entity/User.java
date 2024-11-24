package com.TrungTinhBackend.portfolio_backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String fullName;

    private String img;

    private String password;

    private String address;

    private String phoneNumber;

    private String position;

    private String email;

    private LocalDate birthDay;

    private String university;

    private String hobby;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Skill> skills;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Project> projects;
}
