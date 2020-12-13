package by.bsuir.kp.service;

import by.bsuir.kp.service.impl.AdminServiceImpl;
import by.bsuir.kp.service.impl.UserServiceImpl;

public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();
    private static final UserService userService = new UserServiceImpl();
    private static final AdminService adminService = new AdminServiceImpl();


    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public AdminService getAdminService() {
        return adminService;
    }
}
