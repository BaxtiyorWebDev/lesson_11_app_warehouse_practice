package uz.pdp.online.lesson_11_app_warehouse_practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Warehouse;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.service.WarehouseService;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    WarehouseService warehouseService;

    @PostMapping
    public Result addWarehouse(@RequestBody Warehouse warehouse) {
        Result result = warehouseService.addWarehouse(warehouse);
        return result;
    }

    @GetMapping
    public Page<Warehouse> getWarehousesList(@RequestParam int page) {
        Page<Warehouse> warehousesList = warehouseService.getWarehousesList(page);
        return warehousesList;
    }

    @GetMapping("/{id}")
    public Warehouse getWarehouse(@PathVariable Integer id) {
        Warehouse warehouse = warehouseService.getWarehouse(id);
        return warehouse;
    }

    @PutMapping("/{id}")
    public Result editWarehouse(@PathVariable Integer id, @RequestBody Warehouse warehouse) {
        Result result = warehouseService.editWarehouse(id, warehouse);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result deleteWarehouse(@PathVariable Integer id) {
        Result result = warehouseService.deleteWarehouse(id);
        return result;
    }
}
