package pl.myblog.springblog.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class UserDto {

    @NotBlank(message = "Insert your name")
    private String name;
    @NotBlank(message = "Insert your lastname")
    private String lastname;
    @NotBlank(message = "Your email is not valid")
    @Email
    private String email;
    @Length(min = 6, message = "Your password requires at least 6 characters")
    //@Pattern(regexp = "([A-Z]+.*[0-9]+|[0-9]+.*[A-Z])")  //ustawienie regexpa co najmniej jedna litera i jedna cyfra
    private String password;

}
