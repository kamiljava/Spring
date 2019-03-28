package pl.myblog.springblog.model.dto;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class ContactDto {
    @NotBlank(message = "Enter your name")
    private String name;
    @Email
    @NotBlank(message = "Enter you email")
    private String email;
    private String phone;
    @NotBlank(message = "Enter message")
    private String message;
}
