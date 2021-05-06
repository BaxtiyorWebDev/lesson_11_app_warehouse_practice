package uz.pdp.online.lesson_11_app_warehouse_practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Output;

public interface OutputRepos extends JpaRepository<Output,Integer> {

    boolean existsByFactureNumber(String factureNumber);

    boolean existsByFactureNumberAndIdNot(String factureNumber, Integer id);

}
