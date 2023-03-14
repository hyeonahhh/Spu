package com.example.spu.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="report_review")
public class ReportReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="reason")
    private String reason;

    @Column(name="status")
    private Boolean status;

    @Column(name="content")
    private String content;

    @OneToOne
    @JoinColumn(name="review_id")
    private Review review;
}
