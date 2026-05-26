package ro.mycode.system.security;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserPermissions {

    USER_ADD("User_Add"),
    USER_EDIT("User_Edit"),
    USER_DELETE("User_Delete");

    private String permission;

    public String getPermission() {
        return permission;
    }

}
