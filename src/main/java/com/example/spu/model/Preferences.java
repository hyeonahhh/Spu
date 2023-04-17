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

    // 극호
    @Column(name="favorite")
    private String favorite;

    // 호
    @Column(name="less_favorite")
    private String lessFavorite;

    // 보통
    @Column(name="normal")
    private String normal;

    // 불호
    @Column(name="dislike")
    private String dislike;
}
