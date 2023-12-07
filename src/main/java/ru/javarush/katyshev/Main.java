package ru.javarush.katyshev;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.javarush.katyshev.dao.*;
import ru.javarush.katyshev.entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

public class Main {

    private final SessionFactory sessionFactory;
    private final ActorDAO actorDAO ;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;


    public Main() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Film.class)
                .addAnnotatedClass(FilmText.class)
                .addAnnotatedClass(Inventory.class)
                .addAnnotatedClass(Language.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Rating.class)
                .addAnnotatedClass(Rental.class)
                .addAnnotatedClass(Staff.class)
                .addAnnotatedClass(Store.class)
                .buildSessionFactory();

                actorDAO = new ActorDAO(sessionFactory);
                addressDAO = new AddressDAO(sessionFactory);
                categoryDAO = new CategoryDAO(sessionFactory);
                cityDAO = new CityDAO(sessionFactory);
                countryDAO = new CountryDAO(sessionFactory);
                customerDAO = new CustomerDAO(sessionFactory);
                filmDAO = new FilmDAO(sessionFactory);
                filmTextDAO = new FilmTextDAO(sessionFactory);
                inventoryDAO = new InventoryDAO(sessionFactory);
                languageDAO = new LanguageDAO(sessionFactory);
                paymentDAO = new PaymentDAO(sessionFactory);
                rentalDAO = new RentalDAO(sessionFactory);
                staffDAO = new StaffDAO(sessionFactory);
                storeDAO = new StoreDAO(sessionFactory);
    }

    public static void main(String[] args) {
        Main main = new Main();

        Customer customer = main.createCustomer();

        main.customerReturnInventoryToStore();
        main.customerRentInventory(customer);
        main.createNewFilm();
    }

    private void createNewFilm() {
        try (Session session = sessionFactory.getCurrentSession()){
            session.getTransaction().begin();

            Language language = languageDAO.getById(1);
            List<Category> categories = categoryDAO.getItems(0, 5);
            List<Actor> actors = actorDAO.getItems(0, 20);

            Film film = new Film();
            film.setTitle("Interstellar");
            film.setDescription("Best ever movie...");
            film.setYear(Year.of(2014));
            film.setLanguage(language);
            film.setRentalDuration((byte) 8);
            film.setRentalRate(BigDecimal.valueOf(9.9));
            film.setReplacementCost(BigDecimal.valueOf(24.9));
            film.setRating(Rating.PG13);
            film.setCategories(categories);
            film.setActors(actors);

            filmDAO.save(film);

            FilmText filmText = new FilmText();
            filmText.setFilm(film);
            filmText.setDescription("Humanity's next step will be the greatest");
            filmText.setTitle(film.getTitle());
            filmText.setId(film.getId());
            filmTextDAO.save(filmText);

            session.getTransaction().commit();
        }
    }

    private void customerRentInventory(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()){
            session.getTransaction().begin();

            Film film = filmDAO.getAnyAvailableFilmForRent();
            Store store = storeDAO.getItems(0, 1).get(0);
            Staff staff = store.getStaff();

            Inventory inventory = new Inventory();
            inventory.setFilm(film);
            inventory.setStore(store);
            inventoryDAO.save(inventory);

            Rental rental = new Rental();
            rental.setRentalDate(LocalDateTime.now());
            rental.setCustomer(customer);
            rental.setInventory(inventory);
            rental.setStaff(staff);
            rentalDAO.save(rental);

            Payment payment = new Payment();
            payment.setRental(rental);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setCustomer(customer);
            payment.setAmount(BigDecimal.valueOf(99.9));
            payment.setStaff(staff);
            paymentDAO.save(payment);

            session.getTransaction().commit();
        }
    }

    private void customerReturnInventoryToStore() {
        try (Session session = sessionFactory.getCurrentSession()){
            session.getTransaction().begin();

            Rental rental = rentalDAO.getAnyUnreturnedRental();
            rental.setReturnDate(LocalDateTime.now());
            rentalDAO.save(rental);

            session.getTransaction().commit();
        }
    }

    private Customer createCustomer() {
        try (Session session = sessionFactory.getCurrentSession()){
            session.getTransaction().begin();

            Store store = storeDAO.getItems(0, 1).get(0);

            City city = cityDAO.getByName("Abha");

            Address address = new Address();
            address.setAddress("Some street, 42");
            address.setPhone("88005553535");
            address.setCity(city);
            address.setDistrict("Some district");
            addressDAO.save(address);

            Customer customer = new Customer();
            customer.setStore(store);
            customer.setFirstName("TestName");
            customer.setLastName("TestLastName");
            customer.setAddress(address);
            customer.setIsActive(true);
            customerDAO.save(customer);


            session.getTransaction().commit();

            return customer;
        }
    }
}
