package by.bsuir.kp.dao;

import by.bsuir.kp.bean.Product;
import by.bsuir.kp.bean.User;
import by.bsuir.kp.dao.exception.DAOException;

import java.util.List;

public interface UserDAO {

    User authorization(User user) throws DAOException;

    boolean registration(User user) throws DAOException;

    boolean update(User user) throws DAOException;

    List<Product> initData() throws DAOException;

    List<Product> findProduct(String findProductName) throws DAOException;
}
