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

public class FindProductCommand implements Command {
    private static final String ERROR_MESSAGE = "error_message";
    private static final String PRODUCT_NOT_EXIST = "product_not_exist";

    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final UserService userService = factory.getUserService();

    @Override
    public void execute(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        try {
            String findProductName = (String) inputStream.readObject();
            List<Product> findProductList = userService.findProduct(findProductName);
            outputStream.writeObject(findProductList);
        } catch (ServiceException e) {
            outputStream.writeObject(ERROR_MESSAGE);
        }
    }
}
