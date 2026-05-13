package com.nnk.springboot.ui.proxies;

import com.nnk.springboot.ui.domain.RuleName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "rulename")
public interface RuleNameProxy {
    @GetMapping("/rulename/test")
    List<RuleName> getAllRules();

    @PostMapping("/rulename/add")
    void addRule(@RequestBody RuleName ruleName);

    @GetMapping("/rulename/get/{id}")
    RuleName getRule(@PathVariable("id") Integer id);

    @PostMapping("/rulename/update")
    void updateRule(@RequestBody RuleName ruleName);

    @GetMapping("/rulename/delete/{id}")
    void deleteRule(@PathVariable("id") Integer id);
}