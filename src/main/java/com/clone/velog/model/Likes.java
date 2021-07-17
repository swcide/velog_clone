package com.clone.velog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Likes extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long likesid;

    @Column(nullable = false)
    private String mid;
}
