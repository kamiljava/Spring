package pl.myblog.springblog.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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
    private Boolean active = true;
    private LocalDateTime registered_data = LocalDateTime.now();


    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Post> posts = new HashSet<>();

    //dodawania posta dla uzytkownika
    public void addPost(Post post) {
        this.posts.add(post);
    }

}
