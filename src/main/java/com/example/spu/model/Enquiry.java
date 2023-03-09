package com.example.spu.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name="enquiry")
public class Enquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="e_id")
    private int eId;

    @Column(name="status")
    private Boolean status;

    @Column(name="content")
    private String content;

    @Column(name="date")
    private Date date;

    @Column(name="answer")
    private String answer;

    @OneToOne
    @JoinColumn(name="u_id")
    private User user;
}
