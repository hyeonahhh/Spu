package com.example.spu.model;

import com.example.spu.Enum.Authority;
import com.example.spu.Enum.Platform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="user")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="spu_id")
    private String spuId;

    @Column(name="password")
    private String password;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Column(name="email")
    private String email;

    @Column(name="birth")
    private String birth;

    @Column(name="name")
    private String name;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name = "is_public")
    @ColumnDefault("false")
    private boolean isPublic;

    @Column(name="follower_num")
    private int followerNum;

    @Column(name = "follow_num")
    private int followNum;

    @OneToOne
    @JoinColumn(name="like_id")
    private Like like;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

}
