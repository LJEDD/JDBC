package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final Connection conn = Util.getInstance().getConnection();

    // conn.setAutoCommit(false);

    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() {

        try (Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(255), " +
                    "LastName VARCHAR(255), " +
                    "age INT)");

            conn.commit();
            conn.rollback();
        } catch (SQLException e) {

            e.printStackTrace();

        }

    }



    public void dropUsersTable() {
        try (Statement statement = conn.createStatement())
        { conn.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            conn.commit();
            conn.rollback();
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }


    public void saveUser(String name, String LastName, byte age) {

        String sql = "INSERT INTO users (name, LastName, age) VALUES(?,?,?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, LastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User s imenem  " + name + " dobavlen v basu dannih");
            conn.commit();
            conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    public void removeUserById(long id) {
        try (PreparedStatement pstm = conn.prepareStatement("DELETE FROM user WHERE id = ?")) {
            conn.setAutoCommit(false);
            pstm.setLong(1, id);
            pstm.executeUpdate();
            conn.commit();
            conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM users")) {
            conn.setAutoCommit(false);
            while(resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastname"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
                conn.commit();
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }


    public void cleanUsersTable() {
        try (Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            statement.executeUpdate("TRUNCATE TABLE users");
            conn.commit();
            conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }
}
