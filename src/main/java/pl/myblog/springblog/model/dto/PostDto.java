package pl.myblog.springblog.model.dto;

import lombok.Data;

import pl.myblog.springblog.model.PostCategory;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;


@Data
public class PostDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @Enumerated
    private PostCategory category;


}
