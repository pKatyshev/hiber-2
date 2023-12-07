package ru.javarush.katyshev.dao;

import org.hibernate.SessionFactory;
import ru.javarush.katyshev.entity.Language;

public class LanguageDAO extends GenericDAO<Language> {
    public LanguageDAO(SessionFactory sessionFactory) {
        super(Language.class, sessionFactory);
    }
}
