package pl.myblog.springblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.myblog.springblog.model.Contact;

import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByNameLikeOrEmailLikeOrMessageLike(String name, String email, String message);
}
