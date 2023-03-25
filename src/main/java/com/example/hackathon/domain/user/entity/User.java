package com.example.hackathon.domain.user.entity;

import com.example.hackathon.domain.board.entity.Board;
import com.example.hackathon.domain.certification.entity.Certification;
import com.example.hackathon.domain.global.entity.BaseTimeEntity;
import com.example.hackathon.domain.recipe.entity.Recipe;
import com.example.hackathon.domain.user.constant.UserConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_idx")
    private Long userIdx;

    private String id;

    private String password;

    private String nickName;

    private boolean isDeleted;

    private Long score;

    private Integer level;

    @Enumerated(EnumType.STRING)
    private UserConstants.Role role;

//    @OneToMany(mappedBy = "user")
//    private List<Board> boards=new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<Certification> certifications=new ArrayList<>();
//
//    @OneToMany(mappedBy="user")
//    private List<Recipe> recipes=new ArrayList<>();

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }

}
