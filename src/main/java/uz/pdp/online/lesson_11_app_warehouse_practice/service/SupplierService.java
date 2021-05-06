package uz.pdp.online.lesson_11_app_warehouse_practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Supplier;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.SupplierRepos;

import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    SupplierRepos supplierRepos;

    public Result addSupplier(Supplier supplier) {
        boolean exists = supplierRepos.existsByNameAndPhoneNumber(supplier.getName(), supplier.getPhoneNumber());
        if (exists)
            return new Result("Bunday ta'minotchi mavjud", false);
        supplierRepos.save(supplier);
        return new Result("ta'minotchi qo'shildi", true);
    }

    public Page<Supplier> getSuppliersList(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<Supplier> suppliers = supplierRepos.findAll(pageable);
        return suppliers;
    }

    public Supplier getSupplier(Integer id) {
        Optional<Supplier> optionalSupplier = supplierRepos.findById(id);
        if (!optionalSupplier.isPresent())
            return new Supplier();
        return optionalSupplier.get();
    }

    public Result editSupplier(Integer id, Supplier supplier) {
        boolean exists = supplierRepos.existsByNameAndPhoneNumberAndIdNot(supplier.getName(), supplier.getPhoneNumber(), id);
        if (exists)
            return new Result("Bunday ta'minotchi mavjud, qaytadan urinib ko'ring",false);
        Optional<Supplier> optionalSupplier = supplierRepos.findById(id);
        if (!optionalSupplier.isPresent())
            return new Result("Bunday ta'minotchi mavjud emas",false);
        Supplier editingSupplier = optionalSupplier.get();
        editingSupplier.setName(supplier.getName());
        editingSupplier.setPhoneNumber(supplier.getPhoneNumber());
        editingSupplier.setActive(supplier.isActive());
        supplierRepos.save(editingSupplier);
        return new Result("Ma'lumot tahrirlandi", true);
    }

    public Result deleteSupplier(Integer id) {
        Optional<Supplier> optionalSupplier = supplierRepos.findById(id);
        if (!optionalSupplier.isPresent())
            return new Result("Bunday mijoz mavjud emas",false);
        supplierRepos.delete(optionalSupplier.get());
        return new Result("Ma'lumot o'chirildi",true);
    }
}
