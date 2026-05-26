package ro.mycode.users.dtos;


import lombok.Builder;

@Builder
public record UserRequest(
        String firstName,
         String lastName,
         String email,
        Integer age,
        String password



) {






}
