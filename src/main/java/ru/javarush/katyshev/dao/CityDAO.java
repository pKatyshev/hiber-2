package ru.javarush.katyshev.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.javarush.katyshev.entity.City;

public class CityDAO extends GenericDAO<City>{
    public CityDAO(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    public City getByName(String city) {
        Query<City> query = getCurrentSession().createQuery("from City where city = :NAME", City.class);
        query.setParameter("NAME", city);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
