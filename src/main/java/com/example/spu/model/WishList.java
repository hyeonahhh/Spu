package com.example.spu.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="wish_list")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="u_id")
    private User user;

    @OneToOne
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;
}
