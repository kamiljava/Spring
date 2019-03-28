package pl.myblog.springblog.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;
    private String author;
    private LocalDateTime date_added = LocalDateTime.now();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment() {
    }

    public Comment(String message, String author, Post post) {
        this.message = message;
        this.author = author;
        this.post = post;

    }
}
