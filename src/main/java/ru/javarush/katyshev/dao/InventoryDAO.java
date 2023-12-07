package ru.javarush.katyshev.dao;

import org.hibernate.SessionFactory;
import ru.javarush.katyshev.entity.Inventory;

public class InventoryDAO extends GenericDAO<Inventory>{
    public InventoryDAO(SessionFactory sessionFactory) {
        super(Inventory.class, sessionFactory);
    }
}
