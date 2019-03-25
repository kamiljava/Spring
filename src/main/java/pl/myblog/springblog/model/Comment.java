package pl.myblog.springblog.model;


import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String message;
    private String author;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
