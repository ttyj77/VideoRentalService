package dbConn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnectionMaker implements ConnectionMaker {

    private final String ADDRESS = "jdbc:mysql://localhost/sakila";
    private final String USERNAME = "root";
    private final String PASSWORD = "1234";

    @Override
    public Connection makeConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(ADDRESS, USERNAME, PASSWORD);
//            System.out.println("Mysql 연결 성공!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            throw new RuntimeException();

        }
        return connection;
    }

}
