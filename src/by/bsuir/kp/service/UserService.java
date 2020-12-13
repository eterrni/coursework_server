package by.bsuir.kp.service;

import by.bsuir.kp.bean.Product;
import by.bsuir.kp.bean.User;
import by.bsuir.kp.service.exception.ServiceException;

import java.util.List;

public interface UserService {
    User authorization(User user) throws ServiceException;

    boolean registration(User user) throws ServiceException;

    boolean update(User user) throws ServiceException;

    List<Product> initData() throws ServiceException;

    List<Product> findProduct(String findProductName) throws ServiceException;
}
