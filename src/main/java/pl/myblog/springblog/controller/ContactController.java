package pl.myblog.springblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.myblog.springblog.model.dto.ContactDto;
import pl.myblog.springblog.service.ContactService;

import javax.validation.Valid;

@Controller
public class ContactController {
    private ContactService contactService;
    private Boolean sent = false;
    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }
    @GetMapping("/contact")
    public String contact(Model model, Authentication auth){
        model.addAttribute("auth",auth);
        model.addAttribute("contact", new ContactDto());
        model.addAttribute("sent", sent);
        return "contact";
    }
    @PostMapping("/contact")
    public String contact(@ModelAttribute("contact") @Valid ContactDto contactDto,
                          BindingResult bindingResult,
                          Model model,
                          Authentication auth){
        model.addAttribute("auth",auth);
        model.addAttribute("sent", sent);
        if(bindingResult.hasErrors()){
            return "contact";
        }
        // zapis do DB
        contactService.addContact(contactDto);
        sent = true;
        return "redirect:/contact";
    }
}
