package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    PreparedStatement statment = null;
    String sql;

    public UserDaoJDBCImpl() {

    }
    private  void Conn(String sql){
        Util util = new Util();
        Connection connection = util.getConnection();
        try {
            statment = connection.prepareStatement(sql);
            statment.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (statment != null){
                try {
                    statment.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void createUsersTable() {
        sql = "CREATE TABLE Users( ID int NOT NULL AUTO_INCREMENT, name varchar(100), lastname varchar(100),age INT, " +
                "PRIMARY KEY (ID));";
        Conn(sql);

    }

    public void dropUsersTable() {
        sql = "DROP TABLE users";
        Conn(sql);
    }

    public void saveUser(String name, String lastName, byte age) {
        sql = "INSERT INTO users (name, lastname, age) VALUES (\""
                 + name + "\",\"" +  lastName +  "\"," + age + ");";
        Conn(sql);

    }

    public void removeUserById(long id) {
        sql = "delete from users where id = " + id;
        Conn(sql);
    }

    public List<User> getAllUsers() {
        Util util = new Util();
        Connection connection = util.getConnection();
        sql = "select * from users";
        ArrayList<User> usersList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            while (resultset.next()){
                User user = new User();
                user.setId(resultset.getLong(1));
                user.setName(resultset.getString(2));
                user.setLastName(resultset.getString(3));
                user.setAge(resultset.getByte(4));
                usersList.add(user);

            }

        } catch ( SQLException e){
            e.printStackTrace();
        }
        System.out.println(usersList);


        return usersList;
    }

    public void cleanUsersTable() {
        sql = "DELETE FROM users;";
        Conn(sql);
    }
}
