package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("FirstName1", "LastName1", (byte) 1);
        userService.saveUser("FirstName2", "LastName2", (byte) 2);
        userService.saveUser("FirstName3", "LastName3", (byte) 3);
        userService.saveUser("FirstName4", "LastName4", (byte) 4);

        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }

        userService.removeUserById(3);

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
