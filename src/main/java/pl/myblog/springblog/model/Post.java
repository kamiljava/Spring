package pl.myblog.springblog.model;


import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    @Type(type = "text")
    private String content;
    @Enumerated
    private PostCategory category;
    private LocalDateTime date_added = LocalDateTime.now();
}
