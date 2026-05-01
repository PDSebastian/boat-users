package ro.mycode.boats.service;

import org.springframework.stereotype.Component;
import ro.mycode.boats.exceptions.BoatNotFoundException;
import ro.mycode.boats.model.Boat;
import ro.mycode.boats.repository.BoatRepository;
import ro.mycode.boats.dtos.BoatResponse;
import ro.mycode.boats.mapper.BoatMapper;

import java.util.List;

@Component
public class BoatQueryServiceImpl implements BoatQueryService {
    BoatRepository boatRepository;
    BoatMapper boatMapper;
    BoatQueryServiceImpl(BoatRepository boatRepository, BoatMapper boatMapper) {
        this.boatRepository = boatRepository;
        this.boatMapper = boatMapper;
    }

    @Override
    public List<BoatResponse> getAllBoats() {
        List<Boat> boats = boatRepository.findAll();
        return boats.stream()
                .map(boat -> boatMapper.toDto(boat))
                .toList();
    }

    @Override
    public BoatResponse getBoatById(Long id) {
        Boat boat = boatRepository.findById(id)
                .orElseThrow(() -> new BoatNotFoundException());
        return boatMapper.toDto(boat);
    }

    @Override
    public BoatResponse getBoatByModel(String model) {
     Boat b=  boatRepository.findByModel(model).orElseThrow(()->new BoatNotFoundException());
     return boatMapper.toDto(b);

    }


}
