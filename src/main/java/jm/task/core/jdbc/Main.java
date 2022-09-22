package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {

        UserService userService=new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Name1", "First", (byte) 100);
        userService.saveUser("Name2", "Second", (byte) 100);
        userService.saveUser("Name3", "Third", (byte) 100);
        userService.saveUser("Name4", "Fourth", (byte) 100);

        userService.getAllUsers();

        userService.cleanUsersTable();
        userService.dropUsersTable();



    }
    }



