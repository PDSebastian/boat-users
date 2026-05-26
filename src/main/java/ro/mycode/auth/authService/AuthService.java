package ro.mycode.auth.authService;

import ro.mycode.users.dtos.UserLoginRequest;
import ro.mycode.users.dtos.UserRequest;
import ro.mycode.users.dtos.UserResponse;

public interface AuthService {
    UserResponse login(UserLoginRequest userLoginrequest);
    UserResponse register(UserRequest request);
}
