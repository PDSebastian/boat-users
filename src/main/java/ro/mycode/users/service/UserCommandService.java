package ro.mycode.users.service;

import ro.mycode.users.dtos.UserPatchRequest;
import ro.mycode.users.dtos.UserRequest;
import ro.mycode.users.dtos.UserResponse;

public interface UserCommandService {
    UserResponse addUser( UserRequest userRequest);
    UserResponse updateUser( UserRequest userRequest);
    void deleteUser(Long id);
    UserResponse updatePatchUser(Long id, UserPatchRequest request);




}
