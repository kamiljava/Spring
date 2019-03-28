package pl.myblog.springblog.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public String contact(Model model, Authentication auth){
        model.addAttribute("auth",auth);
        return "contact";
    }

}
