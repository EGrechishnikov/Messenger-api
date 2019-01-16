package by.grechishnikov.messenger.contact.service;

import by.grechishnikov.messenger.contact.dto.ContactDTO;
import by.grechishnikov.messenger.contact.entity.Contact;

import java.util.Set;

/**
 * @author - Evgeniy Grechishnikov
 */
public interface ContactService {

    void saveContact(ContactDTO contact);

    void deleteById(int contactId);

    Set<Contact> findAllContactsByUserId(int userId);

}
