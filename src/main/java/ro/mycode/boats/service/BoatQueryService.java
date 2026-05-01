package ro.mycode.boats.service;

import ro.mycode.boats.dtos.BoatResponse;

import java.util.List;


public interface BoatQueryService {
    List<BoatResponse> getAllBoats();
    BoatResponse getBoatById(Long id);
   BoatResponse getBoatByModel(String model);







}
