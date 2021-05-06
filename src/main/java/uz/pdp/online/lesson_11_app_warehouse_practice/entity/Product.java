package uz.pdp.online.lesson_11_app_warehouse_practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.template.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends AbstractEntity {

    @ManyToOne(optional = false)  // ko'p mahsulotga bitta category, bu yerda oneToOne berish mantiqan notog'ri
    private Category category;

    @OneToOne
    private Attachment photo;

    private String code;

    @ManyToOne(optional = false)// ko'p mahsulotga bitta o'chov birligi, bu yerda oneToOne berish mantiqan notog'ri, chunki
    private Measurement measurement;
}
