package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibirnateUtil;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;


public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory factory = HibirnateUtil.getSessionFactory();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = factory.openSession();
        Transaction txn = session.beginTransaction();

        SQLQuery query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users( ID int NOT NULL AUTO_INCREMENT, name varchar(100), lastname varchar(100),age INT, " +
                "PRIMARY KEY (ID));");
        query.executeUpdate();
        txn.commit();

    }

    @Override
    public void dropUsersTable() {
        Session session = factory.openSession();
        session.beginTransaction();
        Query query = session.createSQLQuery("DROP TABLE IF EXISTS users;");
        query.executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User saveUser = new User(name, lastName, age);
        Session session = factory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(saveUser);
        tx1.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = HibirnateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        User deletUser = session.get(User.class, id = id);
        session.delete(deletUser);
        tx1.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }


    @Override
    public void cleanUsersTable() {

        Session session = factory.openSession();
        session.beginTransaction();
        Query query = session.createSQLQuery("TRUNCATE users;");
        query.executeUpdate();
        session.getTransaction().commit();
    }
}
