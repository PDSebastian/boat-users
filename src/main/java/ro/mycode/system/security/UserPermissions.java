package ro.mycode.system.security;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserPermissions {



    BOAT_ADD("boat:add"),
    BOAT_EDIT("boat:edit"),
    BOAT_DELETE("boat:delete");




    private String permission;
    public String getPermission() {
        return permission;
    }

}
