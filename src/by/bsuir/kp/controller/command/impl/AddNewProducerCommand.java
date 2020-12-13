package by.bsuir.kp.controller.command.impl;

import by.bsuir.kp.controller.command.Command;
import by.bsuir.kp.service.AdminService;
import by.bsuir.kp.service.ServiceFactory;
import by.bsuir.kp.service.exception.ServiceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AddNewProducerCommand implements Command {
    private static final String ERROR_MESSAGE = "error_message";
    private static final String SUCCESSFUL_ADD_PRODUCER_MESSAGE = "successful_add_producer_message";
    private static final String PRODUCER_EXISTS_MESSAGE = "producer_exists_message";
    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final AdminService adminService = factory.getAdminService();

    @Override
    public void execute(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        try {
            String producerName = (String) inputStream.readObject();
            adminService.addNewProducer(producerName);
            outputStream.writeObject(SUCCESSFUL_ADD_PRODUCER_MESSAGE);
        } catch (ServiceException e) {
            if (e.getMessage().equals(PRODUCER_EXISTS_MESSAGE)) {
                outputStream.writeObject(PRODUCER_EXISTS_MESSAGE);
            } else {
                outputStream.writeObject(ERROR_MESSAGE);
            }
        }
    }
}
