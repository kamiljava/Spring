package pl.myblog.springblog.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class UserDto {

    @NotNull
    private String name;
    @NotNull
    private String lastname;
    @NotNull
    @Email
    private String email;
    @Length(min = 6)
    //@Pattern(regexp = "([A-Z]+.*[0-9]+|[0-9]+.*[A-Z])")  //ustawienie regexpa co najmniej jedna litera i jedna cyfra
    private String password;

}
