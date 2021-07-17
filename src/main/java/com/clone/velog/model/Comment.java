package com.clone.velog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long cid;

    @Column(nullable = false)
    private String pid;

    @Column(nullable = false)
    private String mid;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String status;

    public Comment(String pid, String mid, String content, String writer, String status) {
        this.pid = pid;
        this.mid = mid;
        this.content = content;
        this.writer = writer;
        this.status = status;
    }
}
