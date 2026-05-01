package ro.mycode.users.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.system.constants.ErrorConstants;
import ro.mycode.users.exceptions.InvalidAgeException;
import ro.mycode.users.exceptions.InvalidEmailException;
import ro.mycode.users.exceptions.UserAlreadyExistsException;
import ro.mycode.users.exceptions.UserNotFoundexception;
import ro.mycode.users.model.User;
import ro.mycode.users.repository.Userrepository;
import ro.mycode.users.dtos.UserPatchRequest;
import ro.mycode.users.dtos.UserRequest;
import ro.mycode.users.dtos.UserResponse;
import ro.mycode.users.mapper.UserMapper;

@Component
public class UserCommandServiceImpl implements UserCommandService {
    Userrepository userrepository;
    UserMapper usermapper;
    public UserCommandServiceImpl(Userrepository userrepository,UserMapper usermapper) {
        this.userrepository = userrepository;
        this.usermapper = usermapper;

    }



    @Override
    @Transactional
    public UserResponse addUser(UserRequest userRequest) {
        if(userRequest.getAge()<18){
            throw new InvalidAgeException();
        }
        if(userrepository.findByEmail(userRequest.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        }

        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .age(userRequest.getAge())
                .build();


        return usermapper.toDto(userrepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse updateUser( UserRequest userRequest) {
    if(userrepository.findByEmail(userRequest.getEmail()).isPresent()){
        throw new UserAlreadyExistsException();
    }
        User user=User.builder().firstName(userRequest.getFirstName()).lastName(userRequest.getLastName()).age(userRequest.getAge()).build();
        User u= userrepository.save(user);
        return usermapper.toDto(user);


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
