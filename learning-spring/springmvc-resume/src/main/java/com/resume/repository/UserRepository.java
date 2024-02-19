package com.resume.repository;

import com.resume.entity.User;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserRepository {

    private final HibernateTemplate hibernateTemplate;

    @Autowired
    public UserRepository(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

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

    public User getByUsername(String username) {
        DetachedCriteria criteria = DetachedCriteria
                .forEntityName(User.class.getName())
                .add(Restrictions.eq("username", username));

        List<User> users = (List<User>) this.hibernateTemplate.findByCriteria(criteria);

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
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
