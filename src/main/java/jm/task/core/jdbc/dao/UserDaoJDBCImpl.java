package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final String TABLE_NAME = "usersTable";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        String sqlCommand = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(255), " +
                "lastName VARCHAR(255), " +
                "age SMALLINT)";

        Connection connection = Util.getConnection();
        if (connection != null) {
            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                statement.execute(sqlCommand);
                connection.commit();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void dropUsersTable() {

        String sqlCommand = "DROP TABLE IF EXISTS " + TABLE_NAME;

        Connection connection = Util.getConnection();
        if (connection != null) {
            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                statement.execute(sqlCommand);
                connection.commit();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sqlCommand = "INSERT INTO " + TABLE_NAME + " (name, lastName, age) VALUES (?,?,?)";

        Connection connection = Util.getConnection();
        if (connection != null) {
            try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setByte(3, age);
                connection.setAutoCommit(false);
                statement.executeUpdate();
                connection.commit();
                System.out.println("User с именем – " + name + " добавлен в базу данных");
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void removeUserById(long id) {

        String sqlCommand = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

        Connection connection = Util.getConnection();
        if (connection != null) {
            try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
                statement.setLong(1, id);
                connection.setAutoCommit(false);
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<User> getAllUsers() {

        List<User> userList = new LinkedList<>();
        String sqlCommand = "SELECT * FROM " + TABLE_NAME;

        Connection connection = Util.getConnection();
        if (connection != null) {
            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                ResultSet resultSet = statement.executeQuery(sqlCommand);
                connection.commit();
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAge(resultSet.getByte("age"));
                    userList.add(user);
                }
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return userList;
    }

    public void cleanUsersTable() {

        String sqlCommand = "TRUNCATE TABLE " + TABLE_NAME;

        Connection connection = Util.getConnection();
        if (connection != null) {
            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                statement.executeUpdate(sqlCommand);
                connection.commit();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
