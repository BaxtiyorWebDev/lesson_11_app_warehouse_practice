package uz.pdp.online.lesson_11_app_warehouse_practice.payload;

import lombok.Data;

@Data
public class CategoryDto {
    private String name;
    private Integer parentCategoryId;
    private boolean active;
}
