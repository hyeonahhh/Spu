package com.example.spu.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
@Table(name="review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="re_id")
    private int reId;

    @Column(name="like_num")
    private int likeNum;

    @Column(name="text")
    private String text;

    @OneToOne
    @JoinColumn(name="r_id")
    private Restaurant restaurant;

    @OneToOne
    @JoinColumn(name="u_id")
    private User user;

}
