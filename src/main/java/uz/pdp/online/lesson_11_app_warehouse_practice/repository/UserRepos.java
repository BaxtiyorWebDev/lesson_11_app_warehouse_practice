package uz.pdp.online.lesson_11_app_warehouse_practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.User;

public interface UserRepos extends JpaRepository<User, Integer> {

    boolean existsByFirstNameAndPhoneNumber(String firstName, String phoneNumber);

    boolean existsByFirstNameAndPhoneNumberAndIdNot(String firstName, String phoneNumber, Integer id);
}
