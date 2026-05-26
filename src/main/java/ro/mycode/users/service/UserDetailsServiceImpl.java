package ro.mycode.users.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ro.mycode.users.exceptions.UserNotFoundexception;
import ro.mycode.users.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailService{
    UserRepository userrepository;
    public UserDetailsServiceImpl(UserRepository userrepository) {
        this.userrepository = userrepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return (UserDetails) userrepository.findByEmail(email).orElseThrow(()-> new UserNotFoundexception());
    }
}
