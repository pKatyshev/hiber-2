package ru.javarush.katyshev.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.javarush.katyshev.entity.Rental;

import java.util.List;

public class RentalDAO extends GenericDAO<Rental> {
    public RentalDAO(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }

    public Rental getAnyUnreturnedRental() {
        Query<Rental> query = getCurrentSession()
                .createQuery("from Rental where returnDate is null", Rental.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
