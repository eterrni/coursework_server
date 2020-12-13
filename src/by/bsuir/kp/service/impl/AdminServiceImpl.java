package by.bsuir.kp.service.impl;

import by.bsuir.kp.bean.Producer;
import by.bsuir.kp.bean.Product;
import by.bsuir.kp.bean.Provider;
import by.bsuir.kp.bean.User;
import by.bsuir.kp.dao.AdminDAO;
import by.bsuir.kp.dao.DAOFactory;
import by.bsuir.kp.dao.exception.DAOException;
import by.bsuir.kp.service.AdminService;
import by.bsuir.kp.service.exception.ServiceException;

import java.util.List;

public class AdminServiceImpl implements AdminService {

    private static final String PRODUCER_EXISTS_MESSAGE = "producer_exists_message";
    private static final String PROVIDER_EXISTS_MESSAGE = "provider_exists_message";
    private static final DAOFactory factory = DAOFactory.getInstance();
    private static final AdminDAO adminDAO = factory.getAdminDAO();

    @Override
    public void deleteProduct(Integer productId) throws ServiceException {
        try {
            adminDAO.deleteProduct(productId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addNewProduct(Product product) throws ServiceException {
        try {
            adminDAO.addNewProduct(product);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addNewProducer(String producerName) throws ServiceException {
        try {
            adminDAO.addNewProducer(producerName);
        } catch (DAOException e) {
            if (e.getMessage().equals(PRODUCER_EXISTS_MESSAGE)) {
                throw new ServiceException(PRODUCER_EXISTS_MESSAGE, e);
            } else {
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public void addNewProvider(String providerName) throws ServiceException {
        try {
            adminDAO.addNewProvider(providerName);
        } catch (DAOException e) {
            if (e.getMessage().equals(PROVIDER_EXISTS_MESSAGE)) {
                throw new ServiceException(PROVIDER_EXISTS_MESSAGE, e);
            } else {
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public List<User> initUsers() throws ServiceException {
        try {
            return adminDAO.initUsers();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Producer> initChoiceProducer() throws ServiceException {
        try {
            return adminDAO.initChoiceProducer();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Provider> initChoiceProvider() throws ServiceException {
        try {
            return adminDAO.initChoiceProvider();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void appointAnAdministrator(Integer userId) throws ServiceException {
        try {
            adminDAO.appointAnAdministrator(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void appointAnUser(Integer userId) throws ServiceException {
        try {
            adminDAO.appointAnUser(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteUser(Integer userId) throws ServiceException {
        try {
            adminDAO.deleteUser(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
