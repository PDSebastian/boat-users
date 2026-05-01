package ro.mycode.users.dtos;

import ro.mycode.boats.dtos.BoatResponse;

import java.util.List;


public record UserResponse(

        long id,
        String firstName,
        String lastName,
        String email,
        Integer age,
        List<BoatResponse> boatsResponses


) {}