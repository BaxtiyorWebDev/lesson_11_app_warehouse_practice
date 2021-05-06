package uz.pdp.online.lesson_11_app_warehouse_practice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.template.AbstractEntity;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Measurement extends AbstractEntity {
}
