package ro.mycode.auth.authService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.mycode.system.jwt.JWTTokenProvider;
import ro.mycode.system.security.UserPermissions;
import ro.mycode.users.dtos.UserLoginRequest;
import ro.mycode.users.dtos.UserRequest;
import ro.mycode.users.dtos.UserResponse;
import ro.mycode.users.exceptions.UserAlreadyExistsException;
import ro.mycode.users.exceptions.UserNotFoundexception;
import ro.mycode.users.mapper.UserMapper;
import ro.mycode.users.model.User;
import ro.mycode.users.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Component
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public  AuthServiceImpl(UserRepository userRepository, UserMapper userMapper, AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse login(UserLoginRequest userLoginrequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginrequest.email(), userLoginrequest.password())
        );
        User user = userRepository.findByEmail(userLoginrequest.email())
                .orElseThrow(() -> new UserNotFoundexception());

        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAge(),
                jwtTokenProvider.generateToken( user),
                List.of()
        );
    }

    @Override
    public UserResponse register(UserRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPermissions(Set.of(UserPermissions.BOAT_ADD, UserPermissions.BOAT_EDIT, UserPermissions.BOAT_DELETE));
        User saved = userRepository.save(user);

        String token = jwtTokenProvider.generateToken(saved);
        return userMapper.toDtoAuth(saved, token);
    }

}
