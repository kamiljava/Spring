package pl.myblog.springblog.model.dto;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class ContactDto {
    @NotBlank
    private String name;
    @Email
    private String email;
    private String phone;
    @NotBlank
    private String message;
}
