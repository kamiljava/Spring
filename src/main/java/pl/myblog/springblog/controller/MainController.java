package pl.myblog.springblog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.myblog.springblog.model.Post;
import pl.myblog.springblog.model.User;
import pl.myblog.springblog.service.MainService;

import java.util.List;

@RestController
public class MainController {

    MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/")
    public String home(){
        return "Hello my BLOG!!!";
    }
    @GetMapping("/users")
    public List<User> getUsers(){
        return mainService.getAllUsers();                //wykonac zapytanie sql
    }
    @GetMapping("/users/{email}")
    public User getUserByEmail(@PathVariable("email") String email){
        return mainService.getUserByEmail(email);
    }
    //inna wersja
    @GetMapping("/email")
    public User getUserByEmail() {
        return mainService.getUserByEmail("robert@rob.pl");
    }
    @GetMapping("/users/count")
    public Long countAllUser(){
        return mainService.countAllUsers();
    }
    @GetMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id")Long id){
        mainService.updateUserStatus(id);
        return "Status zmieniony";
    }
    @GetMapping("/login/{email}/{password}")
    public String loginCheck(
        @PathVariable("email") String email,
        @PathVariable("password") String password){
        User user = mainService.loginUser(email,password);
        return (user != null) ? user.toString() : "Błąd logowania";
    }
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id")Long id) {
        mainService.deleteUser(id);
        return "Użytkownik usunięty";
    }
    @GetMapping("post/add/{id}/{title}/{content}")
    public Post addPost(
        @PathVariable("id") Long id,
        @PathVariable("title") String title,
        @PathVariable("content") String content) {
        return mainService.addPost(id,title,content);
        }
    }

