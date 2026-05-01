package ro.mycode.boats.mapper;

import org.springframework.stereotype.Component;
import ro.mycode.boats.model.Boat;
import ro.mycode.boats.dtos.BoatRequest;
import ro.mycode.boats.dtos.BoatResponse;
import ro.mycode.users.model.User;

@Component
public class BoatMapper {
    public Boat toEntity(BoatRequest boatRequest, User user) {
       if (boatRequest == null) {
           return null;
       }
       return Boat.builder()
               .model(boatRequest.getModel())
               .color(boatRequest.getColor())
               .size(boatRequest.getSize())
               .user(user)
               .build();

    }
    public BoatResponse toDto(Boat boat) {
        if (boat == null) {
            return null;
        }
        return new  BoatResponse(
                boat.getId(),
                boat.getModel(),
               boat.getColor(),
                boat.getSize()
        );

    }
}
