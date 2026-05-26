package ro.mycode.users.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.users.exceptions.InvalidAgeException;
import ro.mycode.users.exceptions.UserAlreadyExistsException;
import ro.mycode.users.exceptions.UserNotFoundexception;
import ro.mycode.users.model.User;
import ro.mycode.users.repository.UserRepository;
import ro.mycode.users.dtos.UserPatchRequest;
import ro.mycode.users.dtos.UserRequest;
import ro.mycode.users.dtos.UserResponse;
import ro.mycode.users.mapper.UserMapper;

@Component
public class    UserCommandServiceImpl implements UserCommandService {
    UserRepository userrepository;
    UserMapper usermapper;
    public UserCommandServiceImpl(UserRepository userrepository, UserMapper usermapper) {
        this.userrepository = userrepository;
        this.usermapper = usermapper;

    }



    @Override
    @Transactional
    public UserResponse addUser(UserRequest userRequest) {
        if(userRequest.age()<18){
            throw new InvalidAgeException();
        }
        if(userrepository.findByEmail(userRequest.email()).isPresent()){
            throw new UserAlreadyExistsException();
        }

        User user = User.builder()
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .email(userRequest.email())
                .age(userRequest.age())
                .password(userRequest.password())
                .build();


        return usermapper.toDto(userrepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse updateUser(UserRequest userRequest) {
        User user = userrepository.findByEmail(userRequest.email())
                .orElseThrow(()->new  UserNotFoundexception());

        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());
        user.setAge(userRequest.age());

        User updatedUser = userrepository.save(user);
        return usermapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if(!userrepository.existsById(id)){
            throw new UserNotFoundexception();
        }
        userrepository.deleteById(id);

    }

    @Override
    @Transactional
    public UserResponse updatePatchUser(Long id, UserPatchRequest request) {
        User user = userrepository.findById(id)
                .orElseThrow(() -> new UserNotFoundexception());
        if (request.firstName() != null) {
            user.setFirstName(request.firstName());
        }
        if (request.lastName() != null ) {
            user.setLastName(request.lastName());
        }
        if (request.email() != null) {
            user.setEmail(request.email());
        }
        if (request.age() != null) {
            user.setAge(request.age());
        }
        User savedUser = userrepository.save(user);
        return usermapper.toDto(savedUser);
    }
}
