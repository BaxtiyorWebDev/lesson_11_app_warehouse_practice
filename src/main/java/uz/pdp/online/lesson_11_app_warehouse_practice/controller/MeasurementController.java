package uz.pdp.online.lesson_11_app_warehouse_practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Measurement;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.service.MeasurementService;

import java.util.List;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;

    @PostMapping
    public Result addMeasurement(@RequestBody Measurement measurement) {
        return measurementService.addMeasurement(measurement);
    }

    @GetMapping
    public List<Measurement> getMeasurementsList() {
        return measurementService.getMeasurementsList();
    }

    @GetMapping("/{id}")
    public Measurement getMeasurement(@PathVariable Integer id) {
        return measurementService.getMeasurement(id);
    }

    @PutMapping("/{id}")
    public Result editMeasurement(@PathVariable Integer id, @RequestBody Measurement measurement) {
        return measurementService.editMeasurement(id, measurement);
    }

//    @DeleteMapping("/{id}")
//    public Result deleteMeasurement(@PathVariable Integer id) {
//        return measurementService.deleteMeasurement(id);
//    }
}
