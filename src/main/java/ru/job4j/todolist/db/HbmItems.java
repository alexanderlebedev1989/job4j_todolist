package ru.job4j.todolist.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todolist.model.Item;
import ru.job4j.todolist.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

public class HbmItems {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final HbmItems INST = new HbmItems();
    }

    public static HbmItems instOf() {
        return Lazy.INST;
    }

    public Item add(Item item, User user) {
        item.setUser(user);
        this.tx(session -> session.save(item));
        return item;
    }

    public User addUser(User user) {
        this.tx(session -> session.save(user));
        return user;
    }

    public User findByEmail(String email) throws SQLException {
        User user;
        try {
            user = (User) this.tx(
                    session -> session.createQuery("from ru.job4j.todolist.model.User where email='" + email + "'")
                            .getSingleResult());
        } catch (Exception e) {
            throw new SQLException();
        }
        return user;

    }

    public Item findById(int id) {
        Item item = this.tx(session -> session.get(Item.class, id));
        return item;
    }

    public void replace(Integer id, Item item) {
            this.tx(session -> {
            session.update(String.valueOf(id), item);
            return null;
        });
    }

    public List<Item> findAll() {
        return this.tx(session -> session.createQuery("from ru.job4j.todolist.model.Item").list());
    }

    public List<Item> select(boolean condition) {

       return this.tx(session -> session.createQuery("from ru.job4j.todolist.model.Item where done="
               + condition).list());
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}
