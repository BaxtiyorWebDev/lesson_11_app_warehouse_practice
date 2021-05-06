package uz.pdp.online.lesson_11_app_warehouse_practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.*;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.InputDto;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.CurrencyRepos;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.InputRepos;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.SupplierRepos;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.WarehouseRepos;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

@Service
public class InputService {
    @Autowired
    InputRepos inputRepos;
    @Autowired
    WarehouseRepos warehouseRepos;
    @Autowired
    SupplierRepos supplierRepos;
    @Autowired
    CurrencyRepos currencyRepos;

    Input input = new Input();

    public Result addInput(InputDto inputDto) {
        boolean existsFactureNumber = inputRepos.existsByFactureNumber(inputDto.getFactureNumber());
        if (existsFactureNumber)
            return new Result("Bunday faktura raqam mavjud", false);
        input.setDate(inputDto.getDate());
        input.setFactureNumber(inputDto.getFactureNumber());

        Optional<Warehouse> optionalWarehouse = warehouseRepos.findById(inputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new Result("Bunday ombor topilmadi",false);

        Optional<Supplier> optionalSupplier = supplierRepos.findById(inputDto.getSupplierId());
        if (!optionalSupplier.isPresent())
            return new Result("Bunday ta'minotchi topilmadi", false);

        Optional<Currency> optionalCurrency = currencyRepos.findById(inputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Bunday valyuta topilmadi", false);

        input.setWarehouse(optionalWarehouse.get());
        input.setSupplier(optionalSupplier.get());
        input.setCurrency(optionalCurrency.get());
        codeGeneration();
        inputRepos.save(input);
        input = new Input();
        return new Result("Ma'lumot saqlandi", true);
    }

    public Page<Input> getInputsList(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<Input> inputPages = inputRepos.findAll(pageable);
        return inputPages;
    }

    public Input getInput(Integer id) {
        Optional<Input> optionalInput = inputRepos.findById(id);
        if (!optionalInput.isPresent())
            return new Input();
        return optionalInput.get();
    }

    public Result editInput(Integer id, InputDto inputDto) {
        boolean existsFactureNumberAndIdNot = inputRepos.existsByFactureNumberAndIdNot(inputDto.getFactureNumber(), id);
        if (existsFactureNumberAndIdNot)
            return new Result("Bunday kirim mavjud",false);
        Optional<Input> optionalInput = inputRepos.findById(id);
        if (!optionalInput.isPresent())
            return new Result("Ushbu kirim topilmadi",false);
        Input editingInput = optionalInput.get();
        editingInput.setFactureNumber(inputDto.getFactureNumber());
        editingInput.setDate(inputDto.getDate());

        Optional<Currency> optionalCurrency = currencyRepos.findById(inputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Bunday valyuta topilmadi",false);
        Optional<Supplier> optionalSupplier = supplierRepos.findById(inputDto.getSupplierId());
        if(!optionalSupplier.isPresent())
            return new Result("Bunday ta'minotchi topilmadi",false);
        Optional<Warehouse> optionalWarehouse = warehouseRepos.findById(inputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new Result("Bunday ombor topilmadi",false);

        editingInput.setCurrency(optionalCurrency.get());
        editingInput.setSupplier(optionalSupplier.get());
        editingInput.setWarehouse(optionalWarehouse.get());
        inputRepos.save(editingInput);
        return new Result("Ma'lumot tahrirlandi",true);
    }

    public Result deleteInput(Integer id) {
        Optional<Input> optionalInput = inputRepos.findById(id);
        if (!optionalInput.isPresent())
            return new Result("Ma'lumot topilmadi",false);
        inputRepos.delete(optionalInput.get());
        return new Result("Ma'lumot o'chirildi",true);
    }

    public void codeGeneration() {
        List<Input> inputs = inputRepos.findAll();
        TreeSet<Integer> treeSet = new TreeSet();
        for (Input item : inputs) {
            treeSet.add(Integer.valueOf(item.getCode()));
        }
        if (treeSet.size() == 0) {
            input.setCode("1");
        } else {
            Integer last = treeSet.last();
            last++;
            input.setCode(String.valueOf(last));
        }
    }
}
