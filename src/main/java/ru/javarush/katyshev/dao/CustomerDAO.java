package ru.javarush.katyshev.dao;

import org.hibernate.SessionFactory;
import ru.javarush.katyshev.entity.Customer;

public class CustomerDAO extends GenericDAO<Customer> {
    public CustomerDAO(SessionFactory sessionFactory) {
        super(Customer.class, sessionFactory);
    }
}
