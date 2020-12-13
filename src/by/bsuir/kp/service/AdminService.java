package by.bsuir.kp.service;

import by.bsuir.kp.bean.Producer;
import by.bsuir.kp.bean.Product;
import by.bsuir.kp.bean.Provider;
import by.bsuir.kp.bean.User;
import by.bsuir.kp.service.exception.ServiceException;

import java.util.List;

public interface AdminService {
    void deleteProduct(Integer productId) throws ServiceException;

    void addNewProduct(Product product) throws ServiceException;

    void addNewProducer(String producerName) throws ServiceException;

    void addNewProvider(String providerName) throws ServiceException;

    List<User> initUsers() throws ServiceException;

    List<Producer> initChoiceProducer() throws ServiceException;

    List<Provider> initChoiceProvider() throws ServiceException;

    void appointAnAdministrator(Integer userId) throws ServiceException;

    void appointAnUser(Integer userId) throws ServiceException;

    void deleteUser(Integer userId) throws ServiceException;
}
