package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.*;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

public class Util {

    public static SessionFactory getSessionFactory() {

        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("resources/database.properties"))) {
            props.load(in);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String driver = props.getProperty("driver");
        String dialect = props.getProperty("dialect");
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        SessionFactory sessionFactory;
        try {
            Properties hb_props = new Properties();
            hb_props.put("hibernate.connection.driver.class", driver);
            hb_props.put("hibernate.dialect", dialect);
            hb_props.put("hibernate.connection.url", url);
            hb_props.put("hibernate.connection.username", username);
            hb_props.put("hibernate.connection.password", password);

            hb_props.put("hibernate.show_sql", "true");

            Configuration configuration = new Configuration();
            configuration.setProperties(hb_props);
            sessionFactory = configuration.addAnnotatedClass(User.class).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return sessionFactory;
    }

    public static Connection getConnection() {

        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("resources/database.properties"))) {
            props.load(in);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
                    .getDeclaredConstructor()
                    .newInstance();

            return DriverManager.getConnection(url, username, password);

        } catch (Exception ex) {
            System.out.println("Connection failed... :" + ex);
            ex.printStackTrace();
        }
        return null;
    }
}

