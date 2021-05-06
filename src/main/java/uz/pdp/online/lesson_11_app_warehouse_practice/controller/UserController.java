package uz.pdp.online.lesson_11_app_warehouse_practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.User;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.UserDto;
import uz.pdp.online.lesson_11_app_warehouse_practice.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public Result addUser(@RequestBody UserDto userDto) {
        Result result = userService.addUser(userDto);
        return result;
    }

    @GetMapping
    public Page<User> getUsersList(@RequestParam int page) {
        Page<User> usersPage = userService.getUsersPage(page);
        return usersPage;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        User user = userService.getUser(id);
        return user;
    }

    @PutMapping("/{id}")
    public Result editUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        Result result = userService.editUser(id, userDto);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable Integer id) {
        Result result = userService.deleteUser(id);
        return result;
    }
}
