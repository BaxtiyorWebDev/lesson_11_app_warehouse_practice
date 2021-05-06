package uz.pdp.online.lesson_11_app_warehouse_practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Input;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.InputDto;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.service.InputService;


@RestController
@RequestMapping("/input")
public class InputController {
    @Autowired
    InputService inputService;

    @PostMapping
    public Result addInput(@RequestBody InputDto inputDto) {
        Result result = inputService.addInput(inputDto);
        return result;
    }

    @GetMapping
    public Page<Input> getInputsList(@RequestParam int page) {
        Page<Input> inputsList = inputService.getInputsList(page);
        return inputsList;
    }

    @GetMapping("/{id}")
    public Input getInput(@PathVariable Integer id) {
        Input input = inputService.getInput(id);
        return input;
    }

    @PutMapping("/{id}")
    public Result editInput(@PathVariable Integer id, @RequestBody InputDto inputDto) {
        Result result = inputService.editInput(id, inputDto);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result deleteInput(@PathVariable Integer id) {
        Result result = inputService.deleteInput(id);
        return result;
    }
}
