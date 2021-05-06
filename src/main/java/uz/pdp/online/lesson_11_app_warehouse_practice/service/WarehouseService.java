package uz.pdp.online.lesson_11_app_warehouse_practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Warehouse;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.WarehouseRepos;

import java.util.Optional;

@Service
public class WarehouseService {
    @Autowired
    WarehouseRepos warehouseRepos;

    public Result addWarehouse(Warehouse warehouse) {
        boolean existsByName = warehouseRepos.existsByName(warehouse.getName());
        if (existsByName)
            return new Result("Ushbu ombor MO da mavjud", false);
        warehouseRepos.save(warehouse);
        return new Result("Ma'lumot qo'shildi", true);
    }

    public Page<Warehouse> getWarehousesList(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<Warehouse> warehousesPage = warehouseRepos.findAll(pageable);
        return warehousesPage;
    }

    public Warehouse getWarehouse(Integer id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepos.findById(id);
        if (!optionalWarehouse.isPresent())
            return new Warehouse();
        return optionalWarehouse.get();
    }

    public Result editWarehouse(Integer id, Warehouse warehouse) {
        boolean exists = warehouseRepos.existsByNameAndIdNot(warehouse.getName(), id);
        if (exists)
            return new Result("Bunday ombor MO da mavjud",false);
        Optional<Warehouse> optionalWarehouse = warehouseRepos.findById(id);
        if (!optionalWarehouse.isPresent())
            return new Result("Bunday ombor topilmadi",false  );
        Warehouse editingWarehouse = optionalWarehouse.get();
        editingWarehouse.setName(warehouse.getName());
        editingWarehouse.setActive(warehouse.isActive());
        warehouseRepos.save(editingWarehouse);
        return new Result("Ma'lumot tahrirlandi",true);
    }

    public Result deleteWarehouse(Integer id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepos.findById(id);
        if (!optionalWarehouse.isPresent())
            return new Result("Ma'lumot topilmadi",false);
        warehouseRepos.delete(optionalWarehouse.get());
        return new Result("Ma'lumot o'chirild",true);
    }
}

