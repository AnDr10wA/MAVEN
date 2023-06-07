package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {


        UserDao userDao = new UserDaoHibernateImpl();
        userDao.createUsersTable();

        userDao.saveUser("Name1", "LastName5", (byte) 20);
        userDao.saveUser("Name2", "LastName6", (byte) 25);
        userDao.saveUser("Name3", "LastName7", (byte) 31);
        userDao.saveUser("Name4", "LastName4", (byte) 38);

           userDao.removeUserById(5);
           userDao.getAllUsers();
           userDao.cleanUsersTable();
           userDao.dropUsersTable();
    }
}
