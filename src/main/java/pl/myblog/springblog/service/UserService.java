package pl.myblog.springblog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.myblog.springblog.model.User;
import pl.myblog.springblog.model.dto.UserDto;
import pl.myblog.springblog.repository.RoleRepository;
import pl.myblog.springblog.repository.UserRepository;

@Service
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User addUser(UserDto userDto){
        //wprowadzenie wartosci z userDto do user
        User user = new User();
        user.setName(userDto.getName());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        //Szyfrowanie hasła
        String encodedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
        //Wpisujemy zakodowane haslo
        user.setPassword(encodedPassword);
        //ustawienie roli
        user.addRole(roleRepository.getOne((long) 1));
        return userRepository.save(user);
    }
}
