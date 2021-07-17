package com.clone.velog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Posting extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long pid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String tag;

    @Column(nullable = false)
    private String likecount;

    @Column(nullable = false)
    private String mid;

    @Column(nullable = false)
    private String originalfilename;

    @Column(nullable = false)
    private String renamefilename;

    public Posting(String title, String writer, String content, String tag, String likecount, String mid, String originalfilename, String renamefilename) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.tag = tag;
        this.likecount = likecount;
        this.mid = mid;
        this.originalfilename = originalfilename;
        this.renamefilename = renamefilename;
    }
}
