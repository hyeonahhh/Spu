package com.example.spu.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="like")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="favorite")
    private String favorite;

    @Column(name="like")
    private String like;

    @Column(name="less_disLike")
    private String lessDislike;

    @Column(name="dislike")
    private String dislike;
}
