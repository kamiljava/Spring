package pl.myblog.springblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.myblog.springblog.model.Contact;
import pl.myblog.springblog.model.dto.ContactDto;
import pl.myblog.springblog.repository.ContactRepository;

import java.util.List;

@Service
public class ContactService {
    ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
    public Contact addContact(ContactDto contactDto){
        Contact contact = new Contact();
        contact.setName(contactDto.getName());
        contact.setEmail(contactDto.getEmail());
        contact.setPhone(contactDto.getPhone());
        contact.setMessage(contactDto.getMessage());
        return contactRepository.save(contact);
    }

    public List<Contact>getAllContacts(){
        return contactRepository.findAll();
    }
    public void changeFlag(Long id){
        //wyszukaj kontakt po id
        Contact contact = contactRepository.getOne(id);
        //zmodyfikuj flagę na przeciwną
        contact.setFlag(!contact.isFlag());
        //zapisanie zmian
        contactRepository.save(contact);
    }
}
