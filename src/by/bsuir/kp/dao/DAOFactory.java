package by.bsuir.kp.dao;

import by.bsuir.kp.dao.impl.SQLAdminDAO;
import by.bsuir.kp.dao.impl.SQLUserDAO;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private static final UserDAO sqlUserDAO = new SQLUserDAO();

    private static final AdminDAO sqlAdminDAO = new SQLAdminDAO();

    public static DAOFactory getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return sqlUserDAO;
    }

    public AdminDAO getAdminDAO() {
        return sqlAdminDAO;
    }
}
