package by.bsuir.kp.controller.command.impl;

import by.bsuir.kp.controller.command.Command;
import by.bsuir.kp.service.AdminService;
import by.bsuir.kp.service.ServiceFactory;
import by.bsuir.kp.service.exception.ServiceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AddNewProviderCommand implements Command {
    private static final String ERROR_MESSAGE = "error_message";
    private static final String SUCCESSFUL_ADD_PROVIDER_MESSAGE = "successful_add_provider_message";
    private static final String PROVIDER_EXISTS_MESSAGE = "provider_exists_message";
    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final AdminService adminService = factory.getAdminService();

    @Override
    public void execute(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        try {
            String providerName = (String) inputStream.readObject();
            adminService.addNewProvider(providerName);
            outputStream.writeObject(SUCCESSFUL_ADD_PROVIDER_MESSAGE);
        } catch (ServiceException e) {
            if (e.getMessage().equals(PROVIDER_EXISTS_MESSAGE)) {
                outputStream.writeObject(PROVIDER_EXISTS_MESSAGE);
            } else {
                outputStream.writeObject(ERROR_MESSAGE);
            }
        }
    }
}
