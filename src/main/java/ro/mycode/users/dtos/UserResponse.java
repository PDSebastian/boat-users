    package ro.mycode.users.dtos;


    import lombok.Builder;
    import ro.mycode.boats.dtos.BoatResponse;
    import java.util.List;

    @Builder
    public record UserResponse(

            long id,
            String firstName,
            String lastName,
            String email,
            Integer age,
            String token,
            List<BoatResponse> boatsResponses


    ) {}