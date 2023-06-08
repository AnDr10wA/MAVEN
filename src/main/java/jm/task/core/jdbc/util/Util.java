package jm.task.core.jdbc.util;

import org.hibernate.tool.schema.ast.SqlScriptParserException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    protected static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    protected static final String DB_URL = "jdbc:mysql://localhost/maven?";
    protected static final String DB_USERNAME = "root";
    protected static final String DB_PASSWORD = "root";

    public static Connection getConnection() {

        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("You have problems");
        }
        return connection;
    }

}
