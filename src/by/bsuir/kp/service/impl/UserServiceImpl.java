package by.bsuir.kp.service.impl;

import by.bsuir.kp.bean.Product;
import by.bsuir.kp.bean.User;
import by.bsuir.kp.dao.DAOFactory;
import by.bsuir.kp.dao.UserDAO;
import by.bsuir.kp.dao.exception.DAOException;
import by.bsuir.kp.service.UserService;
import by.bsuir.kp.service.exception.ServiceException;
import by.bsuir.kp.service.validator.UserValidator;

import javax.sql.rowset.serial.SerialException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final String INVALID_LOGIN = "invalid_login";
    private static final String INVALID_PASSWORD = "invalid_password";
    private static final String USER_EXISTS_MESSAGE = "user_exists";

    private static final DAOFactory factory = DAOFactory.getInstance();
    private static final UserDAO userDAO = factory.getUserDAO();
    private static final UserValidator validator = UserValidator.getInstance();

    @Override
    public User authorization(User authUser) throws ServiceException {
        try {
            return userDAO.authorization(authUser);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean registration(User user) throws ServiceException {
        try {
            if (!validator.loginValidator(user)) {
                throw new ServiceException(INVALID_LOGIN);
            } else if (!validator.passwordValidator(user)) {
                throw new ServiceException(INVALID_PASSWORD);
            } else return userDAO.registration(user);
        } catch (DAOException e) {
            if (e.getMessage().equals(USER_EXISTS_MESSAGE)) {
                throw new ServiceException(USER_EXISTS_MESSAGE, e);
            }
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(User user) throws ServiceException {
        return false;
    }

    public List<Product> initData() throws ServiceException {
        try {
            return userDAO.initData();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Product> findProduct(String findProductName) throws ServiceException {
        try {
            return userDAO.findProduct(findProductName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
