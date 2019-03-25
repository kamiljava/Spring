package pl.myblog.springblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.myblog.springblog.model.User;
import pl.myblog.springblog.repository.UserRepository;

import java.util.List;

@Service
public class MainService {

    UserRepository userRepository;

    //wstrzykiwanie
    @Autowired   //wstrzykiwanie zaleznosci przez konstruktor
    public MainService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User>getAllUsers(){
        return userRepository.findAll();    // select*from user;      SPRING-obsluga baz danych
    }
    //end-point zwracajacy uzytkowniaka po emailu
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
