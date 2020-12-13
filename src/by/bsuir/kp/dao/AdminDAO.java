package by.bsuir.kp.dao;

import by.bsuir.kp.bean.Producer;
import by.bsuir.kp.bean.Product;
import by.bsuir.kp.bean.Provider;
import by.bsuir.kp.bean.User;
import by.bsuir.kp.dao.exception.DAOException;

import java.util.List;

public interface AdminDAO {
    void deleteProduct(Integer productId) throws DAOException;

    void addNewProduct(Product product) throws DAOException;

    void addNewProducer(String producerName) throws DAOException;

    void addNewProvider(String providerName) throws DAOException;

    List<User> initUsers() throws DAOException;

    List<Producer> initChoiceProducer() throws DAOException;

    List<Provider> initChoiceProvider() throws DAOException;

    void appointAnAdministrator(Integer userId) throws DAOException;

    void appointAnUser(Integer userId) throws DAOException;

    void deleteUser(Integer userId) throws DAOException;
}
