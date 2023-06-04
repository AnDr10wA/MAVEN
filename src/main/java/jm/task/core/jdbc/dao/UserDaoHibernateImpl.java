package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibirnateUtil;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = HibirnateUtil.getSessionFactory().openSession();
        SQLQuery query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users( ID int NOT NULL AUTO_INCREMENT, name varchar(100), lastname varchar(100),age INT, " +
                "PRIMARY KEY (ID));");
    }

    @Override
    public void dropUsersTable() {
        Session session = HibirnateUtil.getSessionFactory().openSession();
        SQLQuery query = session.createSQLQuery("DROP TABLE IF EXISTS users;");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User saveUser = new User(name, lastName, age);
        Session session = HibirnateUtil.getSessionFactory().openSession();
        session.save(saveUser);
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = HibirnateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        User deletUser = session.get(User.class, id = id);
        session.delete(deletUser);
        tx1.commit();
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = HibirnateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from users", User.class).list();
        }

    }


    @Override
    public void cleanUsersTable() {
        Session session = HibirnateUtil.getSessionFactory().openSession();
        SQLQuery query = session.createSQLQuery("TRUNCATE users;");
    }
}
