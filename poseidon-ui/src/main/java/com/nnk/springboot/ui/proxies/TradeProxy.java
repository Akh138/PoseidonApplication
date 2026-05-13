package com.nnk.springboot.ui.proxies;

import com.nnk.springboot.ui.domain.Trade;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "trade")
public interface TradeProxy {
    @GetMapping("/trade/test")
    List<Trade> getAllTrades();

    @PostMapping("/trade/add")
    void addTrade(@RequestBody Trade trade);

    @GetMapping("/trade/get/{id}")
    Trade getTrade(@PathVariable("id") Integer id);

    @PostMapping("/trade/update")
    void updateTrade(@RequestBody Trade trade);

    @GetMapping("/trade/delete/{id}")
    void deleteTrade(@PathVariable("id") Integer id);
}