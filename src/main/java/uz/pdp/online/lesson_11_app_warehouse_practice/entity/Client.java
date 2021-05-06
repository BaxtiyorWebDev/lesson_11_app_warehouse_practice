package uz.pdp.online.lesson_11_app_warehouse_practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.template.AbstractEntity;

import javax.persistence.Entity;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Client extends AbstractEntity {
    private String phoneNumber;
}
