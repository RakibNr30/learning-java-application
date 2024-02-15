package com.resume.service;

import com.resume.entity.UserExperience;
import com.resume.repository.UserExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserExperienceService {

    @Autowired
    private UserExperienceRepository userExperienceRepository;

    public List<UserExperience> getAll() {
        return this.userExperienceRepository.getAll();
    }

    public void save(UserExperience userExperience) {
        this.userExperienceRepository.save(userExperience);
    }

    public UserExperience get(long id) {
        return this.userExperienceRepository.get(id);
    }

    public void update(UserExperience userExperience) {
        this.userExperienceRepository.update(userExperience);
    }

    @Transactional
    public void delete(UserExperience userExperience) {
        this.userExperienceRepository.delete(userExperience);
    }
}