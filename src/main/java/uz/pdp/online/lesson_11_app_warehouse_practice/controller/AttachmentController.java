package uz.pdp.online.lesson_11_app_warehouse_practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Attachment;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.service.AttachmentService;


@RestController
@RequestMapping("attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/upload")
    public Result upload(MultipartHttpServletRequest request) {
        Result result = attachmentService.uploadFile(request);
        return result;
    }

    @GetMapping
    public Page<Attachment> getAttachmentsList(@RequestParam int page) {
        Page<Attachment> attachmentsList = attachmentService.getAttachmentsList(page);
        return attachmentsList;
    }

    @GetMapping("/{id}")
    public Attachment getAttachment(@PathVariable Integer id) {
        Attachment attachment = attachmentService.getAttachment(id);
        return attachment;
    }

    @PutMapping("/{id}")
    public Result editAttachment(@PathVariable Integer id, MultipartHttpServletRequest request) {
        Result result = attachmentService.editAttachment(id, request);
        return result;
    }

//    @DeleteMapping("/{id}")
//    public Result deleteAttachment(@PathVariable Integer id) {
//        Result result = attachmentService.deleteAttachment(id);
//        return result;
//    }
}
