package uz.pdp.online.lesson_11_app_warehouse_practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Attachment;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Category;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Measurement;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Product;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.ProductDto;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.AttachmentRepository;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.CategoryRepos;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.MeasurementRepo;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.ProductRepo;

import java.util.*;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryRepos categoryRepos;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    MeasurementRepo measurementRepo;

    Product product = new Product();

    public Result addProduct(ProductDto productDto) {
        boolean exists = productRepo.existsByNameAndCategoryId(productDto.getName(), productDto.getCategoryId());
        if (exists)
            return new Result("Bunday mahsulot ushbu kategoriyada mavjud", false);
        //CATEGORYNI TEKSHIRISH
        Optional<Category> optionalCategory = categoryRepos.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new Result("Bunday kategoriya mavjud emas", false);

        //PHOTO TEKSHIRISH
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());
        if (!optionalAttachment.isPresent())
            return new Result("Bunday rasm mavjud emas", false);

        //MEASUREMENTNI TEKSHIRISH
        Optional<Measurement> optionalMeasurement = measurementRepo.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent())
            return new Result("Bunday o'lchov birligi mavjud emas", false);

        //SAQLASH
//        Product product = new Product();
        product.setName(productDto.getName());
//        product.setCode("1");//todo generatsiya qilish kerak
        codeGeneration();
        product.setCategory(optionalCategory.get());
        product.setPhoto(optionalAttachment.get());
        product.setMeasurement(optionalMeasurement.get());
        productRepo.save(product);
        product = new Product();
        return new Result("Mahsulot saqlandi", true);
    }

    public void codeGeneration() {
        List<Product> products = productRepo.findAll();
        TreeSet<Integer> treeSet = new TreeSet();
        for (Product item : products) {
            treeSet.add(Integer.valueOf(item.getCode()));
        }
        if (treeSet.size() == 0) {
            product.setCode("1");
        } else {
            Integer last = treeSet.last();
            last++;
            product.setCode(String.valueOf(last));
        }
    }

    public Page<Product> getProductsList(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<Product> productPage = productRepo.findAll(pageable);
        return productPage;
    }

    public Product getProduct(Integer id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (!optionalProduct.isPresent())
            return new Product();
        return optionalProduct.get();
    }

    public Result editProduct(Integer id, ProductDto productDto) {
        boolean exists = productRepo.existsByNameAndCategoryIdAndIdNot(productDto.getName(), productDto.getCategoryId(), id);
        if (exists)
            return new Result("Bunday mahsulot mavjud", false);
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (!optionalProduct.isPresent())
            return new Result("Bunday ma'lumot topilmadi", false);

        Optional<Category> optionalCategory = categoryRepos.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new Result("Bunday kategoriya mavjud emas", false);

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());
        if (!optionalAttachment.isPresent())
            return new Result("Bunday rasm mavjud emas", false);

        Optional<Measurement> optionalMeasurement = measurementRepo.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent())
            return new Result("Bunday o'lchov birligi mavjud emas", false);

        Product editingProduct = optionalProduct.get();
        editingProduct.setName(productDto.getName());
        editingProduct.setMeasurement(optionalMeasurement.get());
        editingProduct.setCategory(optionalCategory.get());
        editingProduct.setPhoto(optionalAttachment.get());
        productRepo.save(editingProduct);
        return new Result("Mahsulot tahrirlandi", true);
    }

    public Result deleteProduct(Integer id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (!optionalProduct.isPresent())
            return new Result("Bunday mahsulot topilmadi",false);
        productRepo.delete(optionalProduct.get());
        return new Result("Mahsulot o'chirildi",true);
    }
}
