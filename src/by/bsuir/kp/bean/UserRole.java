package by.bsuir.kp.bean;

public enum UserRole {
    ADMINISTRATOR(1),
    USER(2);

    private Integer roleId;

    UserRole(Integer roleId) {
        this.roleId = roleId;
    }


    public Integer getRoleId() {
        return roleId;
    }
}
