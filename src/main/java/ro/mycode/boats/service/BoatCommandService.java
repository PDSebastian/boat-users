package ro.mycode.boats.service;

import ro.mycode.boats.dtos.BoatPatchRequest;
import ro.mycode.boats.dtos.BoatRequest;
import ro.mycode.boats.dtos.BoatResponse;

public interface BoatCommandService {
    BoatResponse addBoat( BoatRequest boatRequest);
    BoatResponse updateBoat(Long boatId, BoatRequest boatRequest);
    BoatResponse deleteBoat( Long  boatId);
    BoatResponse updatePatchBoat(Long id, BoatPatchRequest request);




}
