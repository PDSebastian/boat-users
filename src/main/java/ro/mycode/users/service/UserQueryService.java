package ro.mycode.users.service;

import ro.mycode.users.dtos.UserResponse;

import java.util.List;

public interface UserQueryService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    List<UserResponse> getUsersByFirstName(String firstName);
    List<UserResponse> getUserByEmail(String email);

}
