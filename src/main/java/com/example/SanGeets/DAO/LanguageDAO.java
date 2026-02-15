package com.example.SanGeets.DAO;

import com.example.SanGeets.Model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageDAO  extends JpaRepository<Language, Integer> {
    Language findBylanguageName(String languageName);
}
