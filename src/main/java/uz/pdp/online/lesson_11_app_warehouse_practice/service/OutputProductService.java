package uz.pdp.online.lesson_11_app_warehouse_practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.*;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.OutputProductDto;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.*;

import java.util.Optional;

@Service
public class OutputProductService {
    @Autowired
    OutputProductRepos outputProductRepos;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    OutputRepos outputRepos;


    public Result addOutputProduct(OutputProductDto outputProductDto) {
        OutputProduct outputProduct = new OutputProduct();
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setPrice(outputProductDto.getPrice());

        Optional<Product> optionalProduct = productRepo.findById(outputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("Bunday mahsulot topilmadi", false);
        Optional<Output> optionalOutput = outputRepos.findById(outputProductDto.getOutputId());
        if (!optionalOutput.isPresent())
            return new Result("Bunday chiqim topilmadi", false);

        outputProduct.setProduct(optionalProduct.get());
        outputProduct.setOutput(optionalOutput.get());
        outputProductRepos.save(outputProduct);
        return new Result("Ma'lumot saqlandi", true);
    }

    public Page<OutputProduct> getOutputProductPage(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<OutputProduct> productPage = outputProductRepos.findAll(pageable);
        return productPage;
    }

    public OutputProduct getOutputProduct(Integer id) {
        Optional<OutputProduct> optionalOutputProduct = outputProductRepos.findById(id);
        if (!optionalOutputProduct.isPresent())
            return new OutputProduct();
        return optionalOutputProduct.get();
    }

    public Result editOutputProduct(Integer id, OutputProductDto outputProductDto) {
        Optional<OutputProduct> optionalOutputProduct = outputProductRepos.findById(id);
        if (!optionalOutputProduct.isPresent())
            return new Result("Bunday chiqayotgan mahsulot topilmadi",false);
        OutputProduct editingOutputProduct = optionalOutputProduct.get();
        editingOutputProduct.setAmount(outputProductDto.getAmount());
        editingOutputProduct.setPrice(outputProductDto.getPrice());

        Optional<Product> optionalProduct = productRepo.findById(outputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("Ushbu mahsulot topilmadi",false);
        Optional<Output> optionalOutput = outputRepos.findById(outputProductDto.getOutputId());
        if (!optionalOutput.isPresent())
            return new Result("Ushbu chiqim topilmadi",false);

        editingOutputProduct.setProduct(optionalProduct.get());
        editingOutputProduct.setOutput(optionalOutput.get());
        outputProductRepos.save(editingOutputProduct);
        return new Result("Ma'lumot tahrirlandi",true);
    }

    public Result deleteOutputProduct(Integer id) {
        Optional<OutputProduct> optionalOutputProduct = outputProductRepos.findById(id);
        if (!optionalOutputProduct.isPresent())
            return new Result("Ushbu chiqim bo'layotgan mahsulot topilmadi",false);
        outputProductRepos.delete(optionalOutputProduct.get());
        return new Result("Ma'lumot o'chirildi",true);
    }
}
