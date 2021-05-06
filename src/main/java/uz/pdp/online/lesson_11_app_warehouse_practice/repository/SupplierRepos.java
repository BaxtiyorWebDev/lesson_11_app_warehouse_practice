package uz.pdp.online.lesson_11_app_warehouse_practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Supplier;

public interface SupplierRepos extends JpaRepository<Supplier,Integer> {
    boolean existsByNameAndPhoneNumber(String name, String phoneNumber);

    boolean existsByNameAndPhoneNumberAndIdNot(String name, String phoneNumber, Integer id);
}
