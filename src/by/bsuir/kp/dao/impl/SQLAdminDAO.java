package by.bsuir.kp.dao.impl;

import by.bsuir.kp.bean.*;
import by.bsuir.kp.dao.AdminDAO;
import by.bsuir.kp.dao.exception.DAOException;
import by.bsuir.kp.dao.impl.connection_pool.ConnectionPool;
import by.bsuir.kp.dao.impl.connection_pool.exception.ConnectionPoolException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLAdminDAO implements AdminDAO {

    private static final Integer ADMIN_ID = 1;
    private static final String DELETE_PRODUCT = "DELETE FROM `kp_scheme`.`product` WHERE (`id_product` = ?)";
    private static final String ADD_NEW_PRODUCER = "INSERT INTO `kp_scheme`.`producer` (`name`) VALUES (?);\n";
    private static final String ADD_NEW_PROVIDER = "INSERT INTO `kp_scheme`.`provider` (`name`) VALUES (?);\n";
    private static final String ADD_NEW_PRODUCT = "INSERT INTO `kp_scheme`.`product` (`model`, `type`, `producer_id_producer`, `count`, `year`, `provider_id_provider`, `flesh`, `status`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);\n";
    private static final String SELECT_PROVIDER_NAME = "SELECT * FROM `kp_scheme`.`provider` where `kp_scheme`.`provider`.`name` = ?";
    private static final String SELECT_PRODUCER_NAME = "SELECT * FROM `kp_scheme`.`producer` where `kp_scheme`.`producer`.`name` = ?";

    private static final String SELECT_ALL_USERS = "SELECT * FROM `kp_scheme`.`user`";
    private static final String SELECT_ALL_PRODUCERS = "SELECT * FROM `kp_scheme`.`producer`;";
    private static final String SELECT_ALL_PROVIDERS = "SELECT * FROM `kp_scheme`.`provider`;";

    private static final String APPOINT_AN_ADMINISTRATOR = "UPDATE `kp_scheme`.`user` SET `user_role_id_role` = '1' WHERE (`id_user` = ?);\n";
    private static final String APPOINT_AN_USER = "UPDATE `kp_scheme`.`user` SET `user_role_id_role` = '2' WHERE (`id_user` = ?);\n";
    private static final String DELETE_USER = "DELETE FROM `kp_scheme`.`user` WHERE (`id_user` = ?);\n";

    private static final String PRODUCER_EXISTS_MESSAGE = "producer_exists_message";
    private static final String PROVIDER_EXISTS_MESSAGE = "provider_exists_message";
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void deleteProduct(Integer productId) throws DAOException {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = connectionPool.takeConnection();
            ps = cn.prepareStatement(DELETE_PRODUCT);
            ps.setInt(1, productId);
            ps.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(cn, ps);
            } catch (ConnectionPoolException e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    public void addNewProduct(Product product) throws DAOException {
        Connection cn = null;
        PreparedStatement firstPs = null;
        PreparedStatement secondPs = null;
        PreparedStatement thirdPs = null;
        ResultSet rs = null;
        Integer producerId = null;
        Integer providerId = null;
        try {
            cn = connectionPool.takeConnection();
            firstPs = cn.prepareStatement(SELECT_PRODUCER_NAME);
            firstPs.setString(1, product.getProducerName());
            rs = firstPs.executeQuery();
            while (rs.next()) {
                producerId = rs.getInt(1);
            }

            secondPs = cn.prepareStatement(SELECT_PROVIDER_NAME);
            secondPs.setString(1, product.getProviderName());
            rs = secondPs.executeQuery();
            while (rs.next()) {
                providerId = rs.getInt(1);
            }

            thirdPs = cn.prepareStatement(ADD_NEW_PRODUCT);
            thirdPs.setString(1, product.getModel());
            thirdPs.setString(2, product.getType());
            thirdPs.setInt(3, producerId);
            thirdPs.setInt(4, product.getCount());
            thirdPs.setInt(5, product.getYear());
            thirdPs.setInt(6, providerId);
            thirdPs.setInt(7, product.getFlesh());
            thirdPs.setString(8, product.getStatus());
            thirdPs.executeUpdate();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(cn, firstPs, rs);
            } catch (ConnectionPoolException e) {
                throw new DAOException(e);
            }
            try {
                if (secondPs != null) {
                    secondPs.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            try {
                if (thirdPs != null) {
                    thirdPs.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    public void addNewProducer(String producerName) throws DAOException {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = connectionPool.takeConnection();
            ps = cn.prepareStatement(ADD_NEW_PRODUCER);
            ps.setString(1, producerName.toUpperCase());
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DAOException(PRODUCER_EXISTS_MESSAGE, e);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(cn, ps);
            } catch (ConnectionPoolException e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    public void addNewProvider(String providerName) throws DAOException {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = connectionPool.takeConnection();
            ps = cn.prepareStatement(ADD_NEW_PROVIDER);
            ps.setString(1, providerName);
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DAOException(PROVIDER_EXISTS_MESSAGE, e);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(cn, ps);
            } catch (ConnectionPoolException e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    public List<User> initUsers() throws DAOException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> usersList = new ArrayList<>();
        try {
            cn = connectionPool.takeConnection();
            ps = cn.prepareStatement(SELECT_ALL_USERS);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                Integer roleId = Integer.valueOf(rs.getString(4));
                if (roleId.equals(ADMIN_ID)) {
                    user.setRole(UserRole.ADMINISTRATOR);
                } else {
                    user.setRole(UserRole.USER);
                }
                usersList.add(user);
            }
            return usersList;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(cn, ps, rs);
            } catch (ConnectionPoolException e) {
                throw new DAOException("SQLAdminDAO ( initUsers() ) - ConnectionPoolException (finally{})", e);
            }
        }
    }

    @Override
    public List<Producer> initChoiceProducer() throws DAOException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Producer> producerList = new ArrayList<>();
        try {
            cn = connectionPool.takeConnection();
            ps = cn.prepareStatement(SELECT_ALL_PRODUCERS);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producer producer = new Producer();
                producer.setId(rs.getInt(1));
                producer.setName(rs.getString(2));
                producerList.add(producer);
            }
            return producerList;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(cn, ps, rs);
            } catch (ConnectionPoolException e) {
                throw new DAOException("SQLAdminDAO ( initChoiceProducer() ) - ConnectionPoolException (finally{})", e);
            }
        }
    }

    @Override
    public List<Provider> initChoiceProvider() throws DAOException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Provider> providerList = new ArrayList<>();
        try {
            cn = connectionPool.takeConnection();
            ps = cn.prepareStatement(SELECT_ALL_PROVIDERS);
            rs = ps.executeQuery();
            while (rs.next()) {
                Provider provider = new Provider();
                provider.setId(rs.getInt(1));
                provider.setName(rs.getString(2));
                providerList.add(provider);
            }
            return providerList;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(cn, ps, rs);
            } catch (ConnectionPoolException e) {
                throw new DAOException("SQLAdminDAO ( initChoiceProvider() ) - ConnectionPoolException (finally{})", e);
            }
        }
    }

    @Override
    public void appointAnAdministrator(Integer userId) throws DAOException {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = connectionPool.takeConnection();
            ps = cn.prepareStatement(APPOINT_AN_ADMINISTRATOR);
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(cn, ps);
            } catch (ConnectionPoolException e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    public void appointAnUser(Integer userId) throws DAOException {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = connectionPool.takeConnection();
            ps = cn.prepareStatement(APPOINT_AN_USER);
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(cn, ps);
            } catch (ConnectionPoolException e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    public void deleteUser(Integer userId) throws DAOException {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = connectionPool.takeConnection();
            ps = cn.prepareStatement(DELETE_USER);
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(cn, ps);
            } catch (ConnectionPoolException e) {
                throw new DAOException(e);
            }
        }
    }
}
