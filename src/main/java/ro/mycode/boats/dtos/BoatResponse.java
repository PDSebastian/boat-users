package ro.mycode.boats.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoatResponse {
    private long id;
    private String model;
    private String color;
    private int size;

}
