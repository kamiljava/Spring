package pl.myblog.springblog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
