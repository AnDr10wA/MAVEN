package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {



    String sql;

    public UserDaoJDBCImpl() {

    }
    private  void Conn(String sql){
        try(Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
    public void createUsersTable() {
        sql = "CREATE TABLE IF NOT EXISTS Users( ID int NOT NULL AUTO_INCREMENT, name varchar(100), lastname varchar(100),age INT, " +
                "PRIMARY KEY (ID));";
        Conn(sql);
    }
    public void dropUsersTable() {
        sql = "DROP TABLE IF NOT EXISTS users";
        Conn(sql);
    }

    public void saveUser(String name, String lastName, byte age) {
        sql = "INSERT INTO  users (name, lastname, age) VALUES (?, ?, ?);";

        try(Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        sql = "IF NOT EXISTS delete from users where  id = " + id;
        Conn(sql);
    }

    public List<User> getAllUsers() {

        sql = "select * from  users";
        ArrayList<User> usersList = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

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
        sql = "DELETE FROM   users;";
        Conn(sql);
    }
}
