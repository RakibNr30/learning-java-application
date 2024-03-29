package com.springmvc.service;

import com.springmvc.entity.Contact;
import com.springmvc.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getAll() {
        return this.contactRepository.getAll();
    }

    public void save(Contact contact) {
        this.contactRepository.save(contact);
    }

    public Contact get(long id) {
        return this.contactRepository.get(id);
    }

    @Transactional
    public void saveOrUpdate(Contact contact) {
        this.contactRepository.saveOrUpdate(contact);
    }

    public void update(Contact contact) {
        this.contactRepository.update(contact);
    }

    @Transactional
    public void delete(Contact contact) {
        this.contactRepository.delete(contact);
    }

    public Contact getOrSave(long id) {
        Contact contact = this.contactRepository.get(id);

        if (contact == null) {
            contact = new Contact();
            contact.setId(id);
            this.contactRepository.save(contact);
        }

        return contact;
    }
}
