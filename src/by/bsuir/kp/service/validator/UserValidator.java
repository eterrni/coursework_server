package by.bsuir.kp.service.validator;

import by.bsuir.kp.bean.User;

public class UserValidator {

    private static final String LOGIN_REGEXP = "[a-zA-Z][a-zA-Z0-9]{2,14}";
    private static final String PASSWORD_REGEXP = "((?=.*\\d)(?=.*[a-zA-Z]).{5,15})";

    private static final UserValidator instance = new UserValidator();

    public static UserValidator getInstance() {
        return instance;
    }

    public boolean loginValidator(User user) {
        String login = user.getLogin();
        return login != null && login.matches(LOGIN_REGEXP);
    }

    public boolean passwordValidator(User user) {
        String password = user.getPassword();
        return password != null && password.matches(PASSWORD_REGEXP);
    }

}
