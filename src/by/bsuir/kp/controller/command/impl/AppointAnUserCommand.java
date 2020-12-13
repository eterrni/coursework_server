package by.bsuir.kp.controller.command.impl;

import by.bsuir.kp.controller.command.Command;
import by.bsuir.kp.service.AdminService;
import by.bsuir.kp.service.ServiceFactory;
import by.bsuir.kp.service.exception.ServiceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AppointAnUserCommand implements Command {
    private static final String ERROR_MESSAGE = "error_message";
    private static final String SUCCESSFUL_APPOINT_MESSAGE = "successful_appoint";

    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final AdminService adminService = factory.getAdminService();

    @Override
    public void execute(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        try {
            String userIdString = (String) inputStream.readObject();
            Integer userId = Integer.valueOf(userIdString);
            adminService.appointAnUser(userId);
            outputStream.writeObject(SUCCESSFUL_APPOINT_MESSAGE);
        } catch (ServiceException e) {
            outputStream.writeObject(ERROR_MESSAGE);
        }
    }
}
