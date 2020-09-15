package ru.job4j.todolist.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todolist.model.Item;

import java.util.List;

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

    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public Item findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public boolean replace(Integer id, Item item) {
        boolean result = false;
        Session session = sf.openSession();
        session.beginTransaction();
        Item itemOld  = findById(id);
        if (itemOld != null) {
            itemOld.setDescription(item.getDescription());
            result = true;
        }
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public boolean delete(Integer id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = new Item(null);
        item.setId(id);
        session.delete(item);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.todolist.model.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List<Item> select(boolean condition) {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.todolist.model.Item where done=" + condition).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
