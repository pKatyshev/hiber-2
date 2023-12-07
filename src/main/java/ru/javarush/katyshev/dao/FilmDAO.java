package ru.javarush.katyshev.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.javarush.katyshev.entity.Film;

import java.util.List;

public class FilmDAO extends GenericDAO<Film>{
    public FilmDAO(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }

    public Film getAnyAvailableFilmForRent() {
        Query<Film> query = getCurrentSession().createQuery("from Film " +
                "where id not in (select distinct film.id from Inventory)", Film.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
