package uz.pdp.online.lesson_11_app_warehouse_practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Input;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.InputProduct;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.InputProductDto;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Product;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.InputProductRepos;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.InputRepos;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.ProductRepo;

import java.util.Optional;

@Service
public class InputProductService {
    @Autowired
    InputProductRepos inputProductRepos;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    InputRepos inputRepos;


    public Result addInputProduct(InputProductDto inputProductDto) {
        InputProduct inputProduct = new InputProduct();
        inputProduct.setAmount(inputProductDto.getAmount());
        inputProduct.setPrice(inputProductDto.getPrice());
        inputProduct.setExpireDate(inputProductDto.getExpireDate());

        Optional<Product> optionalProduct = productRepo.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("Bunday mahsulot topilmadi", false);
        Optional<Input> optionalInput = inputRepos.findById(inputProductDto.getInputId());
        if (!optionalInput.isPresent())
            return new Result("Bunday kirim topilmadi", false);

        inputProduct.setProduct(optionalProduct.get());
        inputProduct.setInput(optionalInput.get());
        inputProductRepos.save(inputProduct);
        return new Result("Ma'lumot saqlandi", true);
    }

    public Page<InputProduct> getInputProductPage(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<InputProduct> productPage = inputProductRepos.findAll(pageable);
        return productPage;
    }

    public InputProduct getInputProduct(Integer id) {
        Optional<InputProduct> optionalInputProduct = inputProductRepos.findById(id);
        if (!optionalInputProduct.isPresent())
            return new InputProduct();
        return optionalInputProduct.get();
    }

    public Result editInputProduct(Integer id, InputProductDto inputProductDto) {
        Optional<InputProduct> optionalInputProduct = inputProductRepos.findById(id);
        if (!optionalInputProduct.isPresent())
            return new Result("Bunday kiritilayotgan mahsulot topilmadi",false);
        InputProduct editingInputProduct = optionalInputProduct.get();
        editingInputProduct.setAmount(inputProductDto.getAmount());
        editingInputProduct.setPrice(inputProductDto.getPrice());
        editingInputProduct.setExpireDate(inputProductDto.getExpireDate());

        Optional<Product> optionalProduct = productRepo.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("Ushbu mahsulot topilmadi",false);
        Optional<Input> optionalInput = inputRepos.findById(inputProductDto.getInputId());
        if (!optionalInput.isPresent())
            return new Result("Ushbu kirim topilmadi",false);

        editingInputProduct.setProduct(optionalProduct.get());
        editingInputProduct.setInput(optionalInput.get());
        inputProductRepos.save(editingInputProduct);
        return new Result("Ma'lumot tahrirlandi",true);
    }

    public Result deleteInputProduct(Integer id) {
        Optional<InputProduct> optionalInputProduct = inputProductRepos.findById(id);
        if (!optionalInputProduct.isPresent())
            return new Result("Bunday kirim bo'layotgan mahsulot topilmadi",false);
        inputProductRepos.delete(optionalInputProduct.get());
        return new Result("Ma'lumot o'chirildi",true);
    }
}
