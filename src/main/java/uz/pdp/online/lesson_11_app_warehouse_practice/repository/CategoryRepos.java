package uz.pdp.online.lesson_11_app_warehouse_practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Category;

public interface CategoryRepos extends JpaRepository<Category,Integer> {

    boolean existsByNameAndIdNot(String name, Integer id);
}
