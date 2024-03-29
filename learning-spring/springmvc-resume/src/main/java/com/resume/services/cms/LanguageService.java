package com.resume.services.cms;

import com.resume.entities.cms.Language;
import com.resume.repositories.cms.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<Language> findAll() {
        return this.languageRepository.findAll();
    }

    public void save(Language language) {
        this.languageRepository.save(language);
    }

    public Language findById(long id) {
        return this.languageRepository.findById(id);
    }

    public void update(Language language) {
        this.languageRepository.update(language);
    }

    @Transactional
    public void delete(Language language) {
        this.languageRepository.delete(language);
    }
}
