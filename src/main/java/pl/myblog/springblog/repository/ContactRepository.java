package pl.myblog.springblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.myblog.springblog.model.Contact;

public interface ContactRepository extends JpaRepository<Contact,Long> {

}
