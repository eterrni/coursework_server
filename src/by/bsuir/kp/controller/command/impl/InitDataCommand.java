package by.bsuir.kp.controller.command.impl;

import by.bsuir.kp.bean.Product;
import by.bsuir.kp.controller.command.Command;
import by.bsuir.kp.service.ServiceFactory;
import by.bsuir.kp.service.UserService;
import by.bsuir.kp.service.exception.ServiceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class InitDataCommand implements Command {

    private static final String ERROR_MESSAGE = "error_message";
    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final UserService userService = factory.getUserService();

    @Override
    public void execute(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        try {
            List<Product> productList = userService.initData();
            outputStream.writeObject(productList);
        } catch (ServiceException e) {
            outputStream.writeObject(ERROR_MESSAGE);
        }
    }
}
