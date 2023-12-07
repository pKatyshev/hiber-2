package ru.javarush.katyshev.dao;

import org.hibernate.SessionFactory;
import ru.javarush.katyshev.entity.Country;

public class CountryDAO extends GenericDAO<Country> {
    public CountryDAO(SessionFactory sessionFactory) {
        super(Country.class, sessionFactory);
    }
}
