package com.resume.repository;

import com.resume.entity.UserAward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserAwardRepository {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public List<UserAward> getAll() {
        return this.hibernateTemplate.loadAll(UserAward.class);
    }

    @Transactional
    public void save(UserAward userAward) {
        this.hibernateTemplate.save(userAward);
    }

    public UserAward get(long id) {
        return this.hibernateTemplate.get(UserAward.class, id);
    }

    @Transactional
    public void update(UserAward userAward) {
        this.hibernateTemplate.update(userAward);
    }

    @Transactional
    public void delete(UserAward userAward) {
        this.hibernateTemplate.delete(userAward);
    }
}