package ro.mycode.users.service;

import org.springframework.stereotype.Component;
import ro.mycode.users.exceptions.UserNotFoundexception;
import ro.mycode.users.model.User;
import ro.mycode.users.repository.Userrepository;
import ro.mycode.users.dtos.UserResponse;
import ro.mycode.users.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

@Component
public class UserQueryServiceImpl implements UserQueryService {
   Userrepository userrepository;
   UserMapper usermapper;
   public UserQueryServiceImpl(Userrepository userrepository,UserMapper usermapper){
       this.userrepository=userrepository;
       this.usermapper=usermapper;

   }


    @Override
    public List<UserResponse> getAllUsers() {

        List<User> users = userrepository.findAll();
        return users.stream()
                .map(user -> usermapper.toDto(user))
                .toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userrepository.findById( id)
                .orElseThrow(() -> new UserNotFoundexception());
        return usermapper.toDto(user);
    }

    @Override
    public UserResponse getUsersByFirstName(String firstName) {
        Optional<User> u=userrepository.findByFirstName(firstName);
        if(u.isEmpty()){
            throw new UserNotFoundexception();
        }
       return usermapper.toDto(u.get());
    }

    @Override
    public UserResponse getUserByEmail(String email) {
       Optional<User > u =userrepository.findByEmail(email);
       if(u.isEmpty()){
            throw new UserNotFoundexception();
       }
      return usermapper.toDto(u.get());
    }


}
