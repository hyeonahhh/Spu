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
import java.util.List;

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

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    @Column(name="email")
    private String email;

    @Column(name="birth")
    private String birth;

    @Column(name="name")
    private String name;

    @Column(name="phone_number")
    private String phoneNumber;

    private String imageUrl;

    @Column(name = "is_public")
    @ColumnDefault("false")
    private boolean isPublic;

    @Column(name="follower_num")
    private int followerNum;

    @Column(name = "follow_num")
    private int followNum;

    @OneToOne(cascade=CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name="preferences_id")
    private Preferences preferences;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Enquiry> enquiryList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> reviewList;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void authorizeUser() {
        this.authority = Authority.USER;
    }
}
