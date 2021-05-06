package uz.pdp.online.lesson_11_app_warehouse_practice.payload;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String prePassword;
    private boolean active;
    private List<Integer> warehousesId;
}
