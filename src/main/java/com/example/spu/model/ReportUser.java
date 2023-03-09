package com.example.spu.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
@Table(name="report_user")
public class ReportUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ru_id")
    private int ruId;

    @Column(name="reason")
    private String reason;

    @Column(name="status")
    private Boolean status;

    @Column(name="content")
    private String content;

    @OneToOne
    @JoinColumn(name="u_id")
    private User user;

}
