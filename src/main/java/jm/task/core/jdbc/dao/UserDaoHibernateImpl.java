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
    private String sql = null;
    public UserDaoHibernateImpl() {

    }
    private void connectedToBase(String sql){
        Session session = factory.openSession();
        session.beginTransaction();
        SQLQuery query = session.createSQLQuery(sql);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }



    @Override
    public void createUsersTable() {


        sql = "CREATE TABLE IF NOT EXISTS Users( ID int NOT NULL AUTO_INCREMENT, name varchar(100), lastname varchar(100),age INT, " +
                "PRIMARY KEY (ID));";
        connectedToBase(sql);

    }

    @Override
    public void dropUsersTable() {
        sql = "DROP TABLE IF EXISTS users;";
        connectedToBase(sql);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User saveUser = new User(name, lastName, age);
        Session session = factory.openSession();
        session.beginTransaction();
        session.save(saveUser);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = factory.openSession();
        session.beginTransaction();
        User deletUser = session.get(User.class, id = id);
        session.delete(deletUser);
        session.getTransaction().commit();
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

        sql = "TRUNCATE users;";
        connectedToBase(sql);
    }
}
