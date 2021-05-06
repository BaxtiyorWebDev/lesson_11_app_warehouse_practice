package uz.pdp.online.lesson_11_app_warehouse_practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Measurement;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.MeasurementRepo;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;

import java.util.List;
import java.util.Optional;

@Service// component qo'ysak ham bo'ladi, ikkalasinig ham vazifasi bean qilish
public class MeasurementService {

    @Autowired
    MeasurementRepo measurementRepo;

    public Result addMeasurement(Measurement measurement) {
        boolean existsByName = measurementRepo.existsByName(measurement.getName());
        if (existsByName)
            return new Result("Bunday o'lchov birligi mavjud", false);
        measurementRepo.save(measurement);
        return new Result("Muvaffaqqiyatli saqlandi", true);
    }

    //GET LIST, GET ONE, EDIT, DELETE

    public List<Measurement> getMeasurementsList() {
        return measurementRepo.findAll();
    }

    public Measurement getMeasurement(Integer id) {
        Optional<Measurement> optionalMeasurement = measurementRepo.findById(id);
        if (optionalMeasurement.isPresent()) {
            return optionalMeasurement.get();
        }
        return new Measurement();
    }

    public Result editMeasurement(Integer id, Measurement measurement) {
        boolean exists = measurementRepo.existsByNameAndIdNot(measurement.getName(), id);
        if (exists)
            return new Result("Ushbu ma'lumot MO da mavjud", false);
        Optional<Measurement> optionalMeasurement = measurementRepo.findById(id);
        if (!optionalMeasurement.isPresent())
            return new Result("Ma'lumot topilmadi", false);
        Measurement editingMeasurement = optionalMeasurement.get();
        editingMeasurement.setName(measurement.getName());
        editingMeasurement.setActive(measurement.isActive());
        measurementRepo.save(editingMeasurement);
        return new Result("Ma'lumot saqlandi", true);
    }

    public Result deleteMeasurement(Integer id) {
        Optional<Measurement> optionalMeasurement = measurementRepo.findById(id);
        if (!optionalMeasurement.isPresent())
            return new Result("Bunday ma'lumot topilmadi", false);
        measurementRepo.delete(optionalMeasurement.get());
        return new Result("Ma'lumot o'chirildi", true);
    }
}
