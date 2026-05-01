//package ro.mycode.boatsusers.integrationTests;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//import ro.mycode.boats.dtos.BoatRequest;
//import ro.mycode.boats.dtos.BoatResponse;
//import ro.mycode.boats.exceptions.BoatNotFoundException;
//import ro.mycode.boats.repository.BoatRepository;
//import ro.mycode.boats.service.BoatCommandServiceImpl;
//import ro.mycode.users.model.User;
//import ro.mycode.users.repository.Userrepository;
//import ro.mycode.users.service.UserCommandService;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//@ActiveProfiles("test")
//public class BoatCommandServiceIt {
//    @Autowired
//    private BoatRepository boatRepository;
//    @Autowired
//    private BoatCommandServiceImpl  boatCommandService;
//
//    @Autowired
//    private Userrepository userrepository;
//
//
//    @Test
//    @Transactional
//    public void addBoatReturnsOk() throws Exception {
//        String model="a";
//        String color="b";
//        Integer size=10;
//        String firstName="Sebi";
//
//
//        User user = User.builder()
//                .firstName("Sebi")
//                .lastName("Popescu")
//                .email("sebi@test.com")
//                .age(20)
//                .build();
//
//        user = userrepository.save(user);
//
//        BoatRequest b=BoatRequest.builder()
//                .userId(user.getId())
//                .model(model)
//                .size(size)
//                .color(color)
//                .build();
//
//        BoatResponse boatResponse=boatCommandService.addBoat(b);
//
//        assertEquals(model,boatResponse.getModel());
//
//
//
//
//
//
//
//    }
//}
