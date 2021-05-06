package uz.pdp.online.lesson_11_app_warehouse_practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment,Integer> {

}
