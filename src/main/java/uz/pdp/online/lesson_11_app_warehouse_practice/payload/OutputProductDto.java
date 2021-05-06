package uz.pdp.online.lesson_11_app_warehouse_practice.payload;

import lombok.Data;

import java.sql.Date;

@Data
public class OutputProductDto {
    private Integer productId;
    private double amount;
    private double price;
    private Integer outputId;
}
