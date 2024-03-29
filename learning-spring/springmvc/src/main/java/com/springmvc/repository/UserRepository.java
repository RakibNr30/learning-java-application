package com.springmvc.repository;

import com.springmvc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public List<User> getAll() {
        return this.hibernateTemplate.loadAll(User.class);
    }

    @Transactional
    public void save(User user) {
        this.hibernateTemplate.save(user);
    }

    public User get(long id) {
        return this.hibernateTemplate.get(User.class, id);
    }

    @Transactional
    public void update(User user) {
        this.hibernateTemplate.update(user);
    }

    @Transactional
    public void delete(User user) {
        this.hibernateTemplate.delete(user);
    }
}
