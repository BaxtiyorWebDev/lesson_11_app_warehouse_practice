package uz.pdp.online.lesson_11_app_warehouse_practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.AttachmentContent;

public interface AttachmentContentRepo extends JpaRepository<AttachmentContent, Integer> {
    AttachmentContent findByAttachmentId(Integer attachment_id);
}
