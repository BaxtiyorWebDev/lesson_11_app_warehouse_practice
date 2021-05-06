package uz.pdp.online.lesson_11_app_warehouse_practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.*;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.OutputDto;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

@Service
public class OutputService {
    @Autowired
    OutputRepos outputRepos;
    @Autowired
    WarehouseRepos warehouseRepos;
    @Autowired
    ClientRepos clientRepos;
    @Autowired
    CurrencyRepos currencyRepos;

    Output output = new Output();

    public Result addOutput(OutputDto outputDto) {
        boolean existsFactureNumber = outputRepos.existsByFactureNumber(outputDto.getFactureNumber());
        if (existsFactureNumber)
            return new Result("Bunday faktura raqam mavjud", false);
        output.setDate(outputDto.getDate());
        output.setFactureNumber(outputDto.getFactureNumber());

        Optional<Warehouse> optionalWarehouse = warehouseRepos.findById(outputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new Result("Bunday ombor topilmadi", false);

        Optional<Client> optionalClient = clientRepos.findById(outputDto.getClientId());
        if (!optionalClient.isPresent())
            return new Result("Bunday mijoz topilmadi", false);

        Optional<Currency> optionalCurrency = currencyRepos.findById(outputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Bunday valyuta topilmadi", false);

        output.setWarehouse(optionalWarehouse.get());
        output.setClient(optionalClient.get());
        output.setCurrency(optionalCurrency.get());
        codeGeneration();
        outputRepos.save(output);
        output = new Output();
        return new Result("Ma'lumot saqlandi", true);
    }

    public Page<Output> getOutputsList(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<Output> outputsPages = outputRepos.findAll(pageable);
        return outputsPages;
    }

    public Output getOutput(Integer id) {
        Optional<Output> optionalOutput = outputRepos.findById(id);
        if (!optionalOutput.isPresent())
            return new Output();
        return optionalOutput.get();
    }

    public Result editOutput(Integer id, OutputDto outputDto) {
        boolean existsFactureNumberAndIdNot = outputRepos.existsByFactureNumberAndIdNot(outputDto.getFactureNumber(), id);
        if (existsFactureNumberAndIdNot)
            return new Result("Bunday chiqim mavjud",false);
        Optional<Output> optionalOutput = outputRepos.findById(id);
        if (!optionalOutput.isPresent())
            return new Result("Ushbu chiqim topilmadi",false);
        Output editingOutput = optionalOutput.get();
        editingOutput.setFactureNumber(outputDto.getFactureNumber());
        editingOutput.setDate(outputDto.getDate());

        Optional<Currency> optionalCurrency = currencyRepos.findById(outputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Bunday valyuta topilmadi",false);
        Optional<Client> optionalClient = clientRepos.findById(outputDto.getClientId());
        if(!optionalClient.isPresent())
            return new Result("Bunday mijoz topilmadi",false);
        Optional<Warehouse> optionalWarehouse = warehouseRepos.findById(outputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new Result("Bunday ombor topilmadi", false);

        editingOutput.setCurrency(optionalCurrency.get());
        editingOutput.setClient(optionalClient.get());
        editingOutput.setWarehouse(optionalWarehouse.get());
        outputRepos.save(editingOutput);
        return new Result("Ma'lumot tahrirlandi",true);
    }

    public Result deleteOutput(Integer id) {
        Optional<Output> optionalOutput = outputRepos.findById(id);
        if (!optionalOutput.isPresent())
            return new Result("Ma'lumot topilmadi",false);
        outputRepos.delete(optionalOutput.get());
        return new Result("Ma'lumot o'chirildi",true);
    }

    public void codeGeneration() {
        List<Output> outputs = outputRepos.findAll();
        TreeSet<Integer> treeSet = new TreeSet();
        for (Output item : outputs) {
            treeSet.add(Integer.valueOf(item.getCode()));
        }
        if (treeSet.size() == 0) {
            output.setCode("1");
        } else {
            Integer last = treeSet.last();
            last++;
            output.setCode(String.valueOf(last));
        }
    }
}
