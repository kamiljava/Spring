package pl.myblog.springblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.myblog.springblog.model.Contact;
import pl.myblog.springblog.service.ContactService;

import java.util.List;

@Controller
public class AdminController {

    ContactService contactService;
    @Autowired
    public AdminController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/admin")
    public String adminPanel(Model model, Authentication auth){
        //zwrocic liste obiektow klasy contact
        List<Contact> contacts = contactService.getAllContacts();
        model.addAttribute("contacts", contacts);
        return "admin/tables";
    }
    @PostMapping("/changeFlag/{id}")
    public String changeFlag(@PathVariable ("id") Long id){
        //zapytanie typu update
        contactService.changeFlag(id);
        return "redirect:/admin";
    }
}
