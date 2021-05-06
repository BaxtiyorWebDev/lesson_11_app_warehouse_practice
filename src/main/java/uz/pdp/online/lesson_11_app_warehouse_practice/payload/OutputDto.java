package uz.pdp.online.lesson_11_app_warehouse_practice.payload;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class OutputDto {
    private Timestamp date;
    private Integer warehouseId;
    private Integer clientId;
    private Integer currencyId;
    private String factureNumber;
}
