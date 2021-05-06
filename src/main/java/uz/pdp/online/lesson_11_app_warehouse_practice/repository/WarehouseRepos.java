package uz.pdp.online.lesson_11_app_warehouse_practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Warehouse;

public interface WarehouseRepos extends JpaRepository<Warehouse, Integer> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);
}
