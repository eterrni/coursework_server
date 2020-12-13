package by.bsuir.kp.dao.impl.connection_pool.db_properties;

import java.util.ResourceBundle;

public class DBResourceManager {
    private static final DBResourceManager instance = new DBResourceManager();

    private ResourceBundle bundle = ResourceBundle
            .getBundle("by.bsuir.kp.dao.impl.connection_pool.db_properties.db");

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
