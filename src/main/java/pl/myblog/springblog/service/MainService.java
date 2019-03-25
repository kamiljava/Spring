package pl.myblog.springblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.myblog.springblog.model.Post;
import pl.myblog.springblog.model.PostCategory;
import pl.myblog.springblog.model.User;
import pl.myblog.springblog.repository.PostRepository;
import pl.myblog.springblog.repository.UserRepository;

import java.util.List;

@Service
public class MainService {

    UserRepository userRepository;
    PostRepository postRepository;

    //wstrzykiwanie
    @Autowired   //wstrzykiwanie zaleznosci przez konstruktor
    public MainService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<User>getAllUsers(){
        return userRepository.findAll();    // select*from user;      SPRING-obsluga baz danych
    }
    //end-point zwracajacy uzytkowniaka po emailu
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public Long countAllUsers(){
        return userRepository.count();
    }
    public void updateUserStatus(Long id) {
        //select
        User user = userRepository.getOne(id);
        user.setActive(!user.getActive());
        //Update
        userRepository.save(user);
    }
    public User loginUser(String email,String password){
        return userRepository.findByEmailAndPassword(email,password);
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    //utworzenie posta
    public Post addPost(Long id, String title, String content) {
        //znalezienie usera po id
        User user = userRepository.getOne(id);
        //utworz obiekt posta
        Post post = new Post(title, content, PostCategory.PROGRAMOWANIE, user);
        //dodaj posta do zbioru postow obiektu user
        user.addPost(post);
        return postRepository.save(post);
    }
}
