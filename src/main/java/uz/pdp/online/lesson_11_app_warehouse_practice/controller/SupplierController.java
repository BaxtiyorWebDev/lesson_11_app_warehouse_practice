package uz.pdp.online.lesson_11_app_warehouse_practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Supplier;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @PostMapping
    public Result addSupplier(@RequestBody Supplier supplier) {
        Result result = supplierService.addSupplier(supplier);
        return result;
    }

    @GetMapping
    public Page<Supplier> getSuppliersList(@RequestParam int page) {
        Page<Supplier> suppliersList = supplierService.getSuppliersList(page);
        return suppliersList;
    }

    @GetMapping("/{id}")
    public Supplier getSupplier(@PathVariable Integer id) {
        Supplier supplier = supplierService.getSupplier(id);
        return supplier;
    }

    @PutMapping("/{id}")
    public Result editSupplier(@PathVariable Integer id, @RequestBody Supplier supplier) {
        Result result = supplierService.editSupplier(id, supplier);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result deleteSupplier(@PathVariable Integer id) {
        Result result = supplierService.deleteSupplier(id);
        return result;
    }
}
