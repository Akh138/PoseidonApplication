package com.nnk.springboot.ui.proxies;

import com.nnk.springboot.ui.domain.CurvePoint;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "curvepoint") // Nom du service dans Consul
public interface CurvePointProxy {

    @GetMapping("/curvePoint/test")
    List<CurvePoint> getAllPoints();

    @PostMapping("/curvePoint/add")
    void addCurvePoint(@RequestBody CurvePoint curvePoint);

    @GetMapping("/curvePoint/get/{id}")
    CurvePoint getCurvePoint(@PathVariable("id") Integer id);

    @PostMapping("/curvePoint/update")
    void updateCurvePoint(@RequestBody CurvePoint curvePoint);

    @GetMapping("/curvePoint/delete/{id}")
    void deleteCurvePoint(@PathVariable("id") Integer id);
}