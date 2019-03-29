package pl.myblog.springblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String adminPanel(Model model){
        // zwróć listę obiektów klasy Contact
        List<Contact> contacts = contactService.getAllContacts();
        model.addAttribute("contacts",contacts);
        String pattern = "";
        model.addAttribute("pattern",pattern);
        return "admin/tables";
    }
    @PostMapping("/changeFlag/{id}")
    public String changeFlag(@PathVariable("id") Long id){
        // zapytanie typu update
        contactService.changeFlag(id);
        return "redirect:/admin";
    }
    @PostMapping("/search")
    public String search(@ModelAttribute("pattern")String pattern,
                         Model model){
        System.out.println("Pattern " + pattern);
        List<Contact> contacts = contactService.searchContacts("%"+pattern+"%");
        model.addAttribute("contacts", contacts);
        return "admin/tables";
    }

}