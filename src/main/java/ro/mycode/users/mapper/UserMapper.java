package ro.mycode.users.mapper;

import org.springframework.stereotype.Component;
import ro.mycode.boats.mapper.BoatMapper;
import ro.mycode.users.model.User;
import ro.mycode.users.dtos.UserRequest;
import ro.mycode.users.dtos.UserResponse;

import java.util.ArrayList;

@Component
public class UserMapper {
    BoatMapper  boatMapper;
    public UserMapper(BoatMapper boatMapper) {
        this.boatMapper = boatMapper;
    }
    public User toEntity(UserRequest userRequest) {
        if(userRequest == null){
            return null;
        }
        return User.builder().
                firstName(userRequest.getFirstName()).
                lastName(userRequest.getLastName()).
                email(userRequest.getEmail())
                .age(userRequest.getAge())
                .build();
    }
    public UserResponse toDto(User user) {
        if(user == null){
            return null;
        }
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAge(),
                user.getBoats() == null ? new ArrayList<>() :
                        user.getBoats().stream().map(boatMapper::toDto).toList()
        );

    }






}
