package com.nnk.springboot.ui.proxies;

import com.nnk.springboot.ui.domain.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "rating") // Appelle le service "rating" via Consul
public interface RatingProxy {
    @GetMapping("/rating/test")
    List<Rating> getAllRatings();

    @PostMapping("/rating/add")
    void addRating(@RequestBody Rating rating);

    @GetMapping("/rating/get/{id}")
    Rating getRating(@PathVariable("id") Integer id);

    @PostMapping("/rating/update")
    void updateRating(@RequestBody Rating rating);

    @GetMapping("/rating/delete/{id}")
    void deleteRating(@PathVariable("id") Integer id);
}