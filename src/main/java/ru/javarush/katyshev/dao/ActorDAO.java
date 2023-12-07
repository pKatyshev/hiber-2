package ru.javarush.katyshev.dao;

import org.hibernate.SessionFactory;
import ru.javarush.katyshev.entity.Actor;

public class ActorDAO extends GenericDAO<Actor>{
    public ActorDAO(SessionFactory sessionFactory) {
        super(Actor.class, sessionFactory);
    }
}
