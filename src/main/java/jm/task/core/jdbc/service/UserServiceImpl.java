package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDaoJDBCImpl daouser = new UserDaoJDBCImpl();
    public void createUsersTable() {
        daouser.createUsersTable();
    }

    public void dropUsersTable() {
        daouser.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        daouser.saveUser(name,  lastName, age);
    }

    public void removeUserById(long id) {
        daouser.removeUserById(id);
    }

    public List<User> getAllUsers() {

        return daouser.getAllUsers();
    }

    public void cleanUsersTable() {
            daouser.createUsersTable();
    }
}
