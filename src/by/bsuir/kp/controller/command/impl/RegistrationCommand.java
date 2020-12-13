package by.bsuir.kp.controller.command.impl;

import by.bsuir.kp.bean.User;
import by.bsuir.kp.controller.command.Command;
import by.bsuir.kp.service.ServiceFactory;
import by.bsuir.kp.service.UserService;
import by.bsuir.kp.service.exception.ServiceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RegistrationCommand implements Command {

    private static final String ERROR_MESSAGE = "error_message";
    private static final String USER_EXISTS_MESSAGE = "user_exists";
    private static final String INVALID_LOGIN = "invalid_login";
    private static final String INVALID_PASSWORD = "invalid_password";
    private static final String SUCCESSFUL_REGISTRATION = "successful_registration";

    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final UserService userService = factory.getUserService();

    @Override
    public void execute(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        try {
            User user = (User) inputStream.readObject();
            userService.registration(user);
            outputStream.writeObject(SUCCESSFUL_REGISTRATION);
        } catch (ServiceException e) {
            if (e.getMessage().equals(USER_EXISTS_MESSAGE)) {
                outputStream.writeObject(USER_EXISTS_MESSAGE);
            } else if (e.getMessage().equals(INVALID_LOGIN)) {
                outputStream.writeObject(INVALID_LOGIN);
            } else if (e.getMessage().equals(INVALID_PASSWORD)) {
                outputStream.writeObject(INVALID_PASSWORD);
            } else {
                outputStream.writeObject(ERROR_MESSAGE);
            }
        }
    }
}
