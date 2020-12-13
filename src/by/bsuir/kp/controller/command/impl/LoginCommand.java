package by.bsuir.kp.controller.command.impl;

import by.bsuir.kp.bean.User;
import by.bsuir.kp.bean.UserRole;
import by.bsuir.kp.controller.command.Command;
import by.bsuir.kp.service.ServiceFactory;
import by.bsuir.kp.service.UserService;
import by.bsuir.kp.service.exception.ServiceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LoginCommand implements Command {

    private static final String ERROR_MESSAGE = "error_message";
    private static final String SUCCESSFUL_AUTHORIZATION_MESSAGE = "successful_authorization";
    private static final String USER_NOT_EXIST = "user_not_exist";
    private static final String ADMIN_ROLE = "admin";
    private static final String USER_ROLE = "user";

    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final UserService userService = factory.getUserService();

    @Override
    public void execute(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        try {
            User authUser = (User) inputStream.readObject();
            User user = userService.authorization(authUser);
            if (user != null) {
                outputStream.writeObject(SUCCESSFUL_AUTHORIZATION_MESSAGE);
                if (user.getRole().equals(UserRole.ADMINISTRATOR)) {
                    outputStream.writeObject(ADMIN_ROLE);
                } else {
                    outputStream.writeObject(USER_ROLE);
                }
            } else {
                outputStream.writeObject(USER_NOT_EXIST);
            }

        } catch (ServiceException e) {
            outputStream.writeObject(ERROR_MESSAGE);
        }
    }
}
