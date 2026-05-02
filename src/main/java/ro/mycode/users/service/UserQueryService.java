package ro.mycode.users.service;

import ro.mycode.users.dtos.UserResponse;

import java.util.List;

public interface UserQueryService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse getUsersByFirstName(String firstName);
    UserResponse getUserByEmail(String email);

}
