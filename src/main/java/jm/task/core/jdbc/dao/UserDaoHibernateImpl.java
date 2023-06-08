package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibirnateUtil;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;

import static org.hibernate.resource.transaction.spi.TransactionStatus.ACTIVE;
import static org.hibernate.resource.transaction.spi.TransactionStatus.MARKED_ROLLBACK;


public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory factory = HibirnateUtil.getSessionFactory();
    private String sql = null;
    public UserDaoHibernateImpl() {

    }
    private void connectedToBase(String sql){
        try (Session session = factory.openSession();) {
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                Query query = session.createSQLQuery(sql).addEntity(User.class);
                query.executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction.getStatus() == ACTIVE || transaction.getStatus() == MARKED_ROLLBACK) {
                    transaction.rollback();
                }
            }
        }
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

        try (Session session = factory.openSession();){
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                User saveUser = new User(name, lastName, age);
                session.save(saveUser);
                transaction.commit();
            } catch (Exception e) {
                if (transaction.getStatus() == ACTIVE || transaction.getStatus() == MARKED_ROLLBACK) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.openSession();){
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                User deletUser = session.get(User.class, id = id);
                session.delete(deletUser);
                transaction.commit();
            } catch (Exception e) {
                if (transaction.getStatus() == ACTIVE || transaction.getStatus() == MARKED_ROLLBACK) {
                    transaction.rollback();
                }
            }
        }
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
