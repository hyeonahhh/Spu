package com.example.spu.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Data
@RequiredArgsConstructor
@Table(name="appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="date")
    private Date date;

    @Column(name="time")
    private Time time;

    @Column(name="is_spu")
    private Boolean isSpu;

    @Column(name="share_list")
    private String shareList;

}
