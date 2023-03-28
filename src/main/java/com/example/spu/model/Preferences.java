package com.example.spu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="preferences")
public class Preferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="favorite")
    private String favorite;

    @Column(name="normal")
    private String normal;

    @Column(name="less_disLike")
    private String lessDislike;

    @Column(name="dislike")
    private String dislike;
}
