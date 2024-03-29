package com.resume.services.cms;

import com.resume.entities.cms.SocialAccount;
import com.resume.repositories.cms.SocialAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SocialAccountService {

    private final SocialAccountRepository socialAccountRepository;

    @Autowired
    public SocialAccountService(SocialAccountRepository socialAccountRepository) {
        this.socialAccountRepository = socialAccountRepository;
    }

    public List<SocialAccount> findAll() {
        return this.socialAccountRepository.findAll();
    }

    public void save(SocialAccount socialAccount) {
        this.socialAccountRepository.save(socialAccount);
    }

    public SocialAccount findById(long id) {
        return this.socialAccountRepository.findById(id);
    }

    public void update(SocialAccount socialAccount) {
        this.socialAccountRepository.update(socialAccount);
    }

    @Transactional
    public void delete(SocialAccount socialAccount) {
        this.socialAccountRepository.delete(socialAccount);
    }
}
