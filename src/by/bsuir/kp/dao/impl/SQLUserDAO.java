package by.bsuir.kp.dao.impl;

import by.bsuir.kp.bean.Product;
import by.bsuir.kp.bean.User;
import by.bsuir.kp.bean.UserRole;
import by.bsuir.kp.dao.UserDAO;
import by.bsuir.kp.dao.exception.DAOException;
import by.bsuir.kp.dao.impl.connection_pool.ConnectionPool;
import by.bsuir.kp.dao.impl.connection_pool.exception.ConnectionPoolException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUserDAO implements UserDAO {

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String ROLE_ID_ADMINISTRATOR = "1";

    private static final String SELECT_USER_LOGIN_PASSWORD = "SELECT * FROM kp_scheme.user " +
            "where user.login =? AND user.password =?";

    private static final String SELECT_ALL_PRODUCTS = "SELECT kp_scheme.product.id_product, kp_scheme.product.model, kp_scheme.product.type, kp_scheme.producer.name,\n" +
            "kp_scheme.product.count, kp_scheme.product.year, kp_scheme.provider.name, kp_scheme.product.flesh, kp_scheme.product.status\n" +
            "FROM kp_scheme.product\n" +
            "inner join kp_scheme.producer join kp_scheme.provider\n" +
            "on kp_scheme.product.producer_id_producer = kp_scheme.producer.id_producer\n" +
            "and kp_scheme.product.provider_id_provider = kp_scheme.provider.id_provider";

    private static final String ADD_NEW_USER = "INSERT INTO `kp_scheme`.`user` (`login`, `password`, `user_role_id_role`) VALUES (?, ?, '2');";

    private static final String FIND_PRODUCT = "SELECT kp_scheme.product.id_product, kp_scheme.product.model, kp_scheme.product.type, kp_scheme.producer.name,\n" +
            "kp_scheme.product.count, kp_scheme.product.year, kp_scheme.provider.name, kp_scheme.product.flesh, kp_scheme.product.status\n" +
            "FROM kp_scheme.product\n" +
            "inner join kp_scheme.producer join kp_scheme.provider\n" +
            "on kp_scheme.product.producer_id_producer = kp_scheme.producer.id_producer\n" +
            "and kp_scheme.product.provider_id_provider = kp_scheme.provider.id_provider\n" +
            "where kp_scheme.product.model = ?\n";

    private static final String USER_EXISTS_MESSAGE = "user_exists";

    @Override
    public User authorization(User authUser) throws DAOException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            cn = connectionPool.takeConnection();
            ps = cn.prepareStatement(SELECT_USER_LOGIN_PASSWORD);
            ps.setString(1, authUser.getLogin());
            ps.setString(2, authUser.getPassword());
            rs = ps.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setLogin(authUser.getLogin());
                String roleId = rs.getString(4);
                if (roleId.equals(ROLE_ID_ADMINISTRATOR)) {
                    user.setRole(UserRole.ADMINISTRATOR);
                } else {
                    user.setRole(UserRole.USER);
                }
            }
            return user;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Authorization exception", e);
        } finally {
            try {
                connectionPool.closeConnection(cn, ps, rs);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Error close connection", e);
            }
        }
    }

    @Override
    public boolean registration(User user) throws DAOException {
        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = connectionPool.takeConnection();
            ps = cn.prepareStatement(ADD_NEW_USER);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());

            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DAOException(USER_EXISTS_MESSAGE, e);
        } catch (SQLException e) {
            throw new DAOException("SQLUserDAO ( registartion() ) - SQLException", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("SQLUserDAO ( registartion() ) - ConnectionPoolException", e);
        } finally {
            try {
                connectionPool.closeConnection(cn, ps);
            } catch (ConnectionPoolException e) {
                throw new DAOException("SQLUserDAO ( registartion() ) - ConnectionPoolException (finally{})", e);
            }
        }
        return false;
    }

    @Override
    public boolean update(User user) throws DAOException {
        return false;
    }

    @Override
    public List<Product> initData() throws DAOException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Product> productList = new ArrayList<>();
        try {
            cn = connectionPool.takeConnection();
            ps = cn.prepareStatement(SELECT_ALL_PRODUCTS);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt(1));
                product.setModel(rs.getString(2));
                product.setType(rs.getString(3));
                product.setProducerName(rs.getString(4));
                product.setCount(rs.getInt(5));
                product.setYear(rs.getInt(6));
                product.setProviderName(rs.getString(7));
                product.setFlesh(rs.getInt(8));
                product.setStatus(rs.getString(9));

                productList.add(product);
            }
            return productList;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(cn, ps, rs);
            } catch (ConnectionPoolException e) {
                throw new DAOException("SQLUserDAO ( registartion() ) - ConnectionPoolException (finally{})", e);
            }
        }
    }

    @Override
    public List<Product> findProduct(String findProductName) throws DAOException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Product> findProductList = new ArrayList<>();
        try {
            cn = connectionPool.takeConnection();
            ps = cn.prepareStatement(FIND_PRODUCT);
            ps.setString(1, findProductName);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt(1));
                product.setModel(rs.getString(2));
                product.setType(rs.getString(3));
                product.setProducerName(rs.getString(4));
                product.setCount(rs.getInt(5));
                product.setYear(rs.getInt(6));
                product.setProviderName(rs.getString(7));
                product.setFlesh(rs.getInt(8));
                product.setStatus(rs.getString(9));

                findProductList.add(product);
            }
            if (findProductList.size() != 0) {
                return findProductList;
            } else {
                return null;
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(cn, ps, rs);
            } catch (ConnectionPoolException e) {
                throw new DAOException("SQLUserDAO ( registartion() ) - ConnectionPoolException (finally{})", e);
            }
        }
    }
}
