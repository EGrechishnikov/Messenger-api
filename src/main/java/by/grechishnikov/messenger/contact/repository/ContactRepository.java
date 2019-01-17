package by.grechishnikov.messenger.contact.repository;

import by.grechishnikov.messenger.contact.entity.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author - Evgeniy Grechishnikov
 */
public interface ContactRepository extends CrudRepository<Contact, Integer> {

    List<Contact> findAllByUserId(int userId);

}
