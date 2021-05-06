package uz.pdp.online.lesson_11_app_warehouse_practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Product;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.ProductDto;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public Result addProduct(@RequestBody ProductDto productDto) {
        Result result = productService.addProduct(productDto);
        return result;
    }

    @GetMapping
    public Page<Product> getProductsList(@RequestParam int page) {
        Page<Product> productsList = productService.getProductsList(page);
        return productsList;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Integer id) {
        Product product = productService.getProduct(id);
        return product;
    }

    @PutMapping("/{id}")
    public Result editProduct(@PathVariable Integer id, @RequestBody ProductDto productDto) {
        Result result = productService.editProduct(id, productDto);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result deleteProduct(@PathVariable Integer id) {
        Result result = productService.deleteProduct(id);
        return result;
    }
}
