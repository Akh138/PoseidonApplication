package com.nnk.springboot.ui.proxies;

import com.nnk.springboot.ui.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "user") // Appelle le service "user" via Consul
public interface UserProxy {

    @GetMapping("/user/test")
    List<User> getAllUsers();

    @PostMapping("/user/add")
    void addUser(@RequestBody User user);

    @GetMapping("/user/get/{id}")
    User getUser(@PathVariable("id") Integer id);

    @PostMapping("/user/update")
    void updateUser(@RequestBody User user);

    @GetMapping("/user/delete/{id}")
    void deleteUser(@PathVariable("id") Integer id);

    // MÉTHODE CRUCIALE POUR LE LOGIN
    @GetMapping("/user/getByUsername/{username}")
    User getByUsername(@PathVariable("username") String username);
}