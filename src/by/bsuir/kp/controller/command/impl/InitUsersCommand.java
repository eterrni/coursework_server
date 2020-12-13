package by.bsuir.kp.controller.command.impl;

import by.bsuir.kp.bean.Product;
import by.bsuir.kp.bean.User;
import by.bsuir.kp.controller.command.Command;
import by.bsuir.kp.service.AdminService;
import by.bsuir.kp.service.ServiceFactory;
import by.bsuir.kp.service.UserService;
import by.bsuir.kp.service.exception.ServiceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class InitUsersCommand implements Command {
    private static final String ERROR_MESSAGE = "error_message";
    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final AdminService adminService = factory.getAdminService();

    @Override
    public void execute(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException {
        try {
            List<User> usersList = adminService.initUsers();
            outputStream.writeObject(usersList);
        } catch (ServiceException e) {
            outputStream.writeObject(ERROR_MESSAGE);
        }
    }
}
