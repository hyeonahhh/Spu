package com.example.spu.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="u_id")
    private int uId;

    @Column(name="id")
    private String id;

    @Column(name="password")
    private String password;

    @Column(name="platform")
    private String platform;

    @Column(name="birth")
    private String birth;

    @Column(name="name")
    private String name;

    @Column(name = "is_public")
    @ColumnDefault("false")
    private boolean isPublic;

    @Column(name="follower_num")
    private int followerNum;

    @Column(name = "follow_num")
    private int followNum;

    @OneToOne
    @JoinColumn(name="l_id")
    private Like like;
}
