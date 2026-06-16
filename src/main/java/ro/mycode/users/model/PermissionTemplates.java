package ro.mycode.users.model;

import ro.mycode.system.security.UserPermissions;

import java.util.Set;

public class PermissionTemplates {
    public static final Set<UserPermissions> permissions = Set.of(
            UserPermissions.BOAT_ADD,
            UserPermissions.BOAT_DELETE,
            UserPermissions.BOAT_EDIT






    );
}
