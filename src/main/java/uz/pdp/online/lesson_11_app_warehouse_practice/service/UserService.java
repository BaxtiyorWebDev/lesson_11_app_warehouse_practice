package uz.pdp.online.lesson_11_app_warehouse_practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.User;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Warehouse;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.UserDto;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.UserRepos;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.WarehouseRepos;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Service
public class UserService {
    @Autowired
    UserRepos userRepos;
    @Autowired
    WarehouseRepos warehouseRepos;

    User user = new User();

    public Result addUser(UserDto userDto) {
        boolean exists = userRepos.existsByFirstNameAndPhoneNumber(userDto.getFirstName(), userDto.getPhoneNumber());
        if (exists)
            return new Result("Ushbu user MO da mavjud", false);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setActive(userDto.isActive());
        if (userDto.getPassword().equals(userDto.getPrePassword())) {
            user.setPassword(userDto.getPassword());
        } else {
            return new Result("Parol xato!", false);
        }
        codeGeneration();
        List<Warehouse> allById = warehouseRepos.findAllById(userDto.getWarehousesId());
        user.setWarehouses(allById);
        userRepos.save(user);
        user = new User();
        return new Result("User qo'shildi", true);
    }


    public Page<User> getUsersPage(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return userRepos.findAll(pageable);
    }

    public User getUser(Integer id) {
        Optional<User> optionalUser = userRepos.findById(id);
        if (!optionalUser.isPresent())
            return new User();
        return optionalUser.get();
    }

    public Result editUser(Integer id, UserDto userDto) {
        boolean exists = userRepos.existsByFirstNameAndPhoneNumberAndIdNot(userDto.getFirstName(), userDto.getPhoneNumber(), id);
        if (exists)
            return new Result("Ushbu user MO da mavjud",false);
        Optional<User> optionalUser = userRepos.findById(id);
        if (!optionalUser.isPresent())
            return new Result("Ushbu user MO da mavjud emas",false);
        User editingUser = optionalUser.get();
        editingUser.setFirstName(userDto.getFirstName());
        editingUser.setLastName(userDto.getLastName());
        editingUser.setPhoneNumber(userDto.getPhoneNumber());
        editingUser.setPassword(userDto.getPassword());
        editingUser.setActive(userDto.isActive());
        codeGeneration();
        List<Warehouse> allById = warehouseRepos.findAllById(userDto.getWarehousesId());
        editingUser.setWarehouses(allById);
        user = new User();
        userRepos.save(editingUser);
        return new Result("Ma'lumot tahrirlandi",true);
    }
    public void codeGeneration() {
        List<User> users = userRepos.findAll();
        TreeSet<Integer> treeSet = new TreeSet();
        for (User item : users) {
            treeSet.add(Integer.valueOf(item.getCode()));
        }
        if (treeSet.size() == 0) {
            user.setCode("1");
        } else {
            Integer last = treeSet.last();
            last++;
            user.setCode(String.valueOf(last));
        }
    }

    public Result deleteUser(Integer id) {
        Optional<User> optionalUser = userRepos.findById(id);
        if (!optionalUser.isPresent())
            return new Result("Ma'lumot topilmadi",false);
        userRepos.delete(optionalUser.get());
        return new Result("Ma'lumot o'chirildi",true);
    }
}
