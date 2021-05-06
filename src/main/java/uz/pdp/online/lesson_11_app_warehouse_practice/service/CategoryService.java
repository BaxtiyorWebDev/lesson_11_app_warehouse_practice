package uz.pdp.online.lesson_11_app_warehouse_practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Category;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.CategoryDto;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.CategoryRepos;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepos categoryRepos;

    public Result addCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        if (categoryDto.getParentCategoryId() != null) {
            Optional<Category> optionalCategory = categoryRepos.findById(categoryDto.getParentCategoryId());
            if (!optionalCategory.isPresent())
                return new Result("Bunday ota kategoriya mavjud emas", false);
            category.setParentCategory(optionalCategory.get());
        }
        categoryRepos.save(category);
        return new Result("Muvaffaqqiyatli saqlandi", true);
    }

    public Page getCategoryList(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return categoryRepos.findAll(pageable);
    }

    public Category getCategory(Integer id) {
        Optional<Category> optionalCategory = categoryRepos.findById(id);
        if (!optionalCategory.isPresent())
            return new Category();
        return optionalCategory.get();
    }

    public Result editCategory(Integer id, CategoryDto categoryDto) {
        boolean exists = categoryRepos.existsByNameAndIdNot(categoryDto.getName(), id);
        if (exists)
            return new Result("Bunday ma'lumot mavjud",false);
        Optional<Category> optionalCategory = categoryRepos.findById(id);
        Optional<Category> optionalParentCategory = categoryRepos.findById(categoryDto.getParentCategoryId());
        if (!optionalCategory.isPresent()&&!optionalParentCategory.isPresent())
            return new Result("Bunday ma'lumot topilmadi",false);
        Category editingCategory = optionalCategory.get();
        editingCategory.setName(categoryDto.getName());
        editingCategory.setParentCategory(optionalParentCategory.get());
        editingCategory.setActive(categoryDto.isActive());
        categoryRepos.save(editingCategory);
        return new Result("Ma'lumot o'zgartirildi",true);
    }//todo edit da xato bo'lishi mumkin

    public Result deleteCategory(Integer id) {
        Optional<Category> optionalCategory = categoryRepos.findById(id);
        if (!optionalCategory.isPresent())
            return new Result("Bunday ma'lumot topilmadi",false);
        categoryRepos.delete(optionalCategory.get());
        return new Result("Ma'lumot o'chirildi",true);
    }//todo delete da ham xatolik bo'lishi mumkin
}
