package com.nnk.springboot.ui.proxies;

import com.nnk.springboot.ui.domain.BidList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// On dit à Feign d'appeler le service nommé "bidlist" enregistré dans Consul
@FeignClient(name = "bidlist")
public interface BidListProxy {

    @GetMapping("/bidList/test")
    List<BidList> getAllBids();

    @PostMapping("/bidList/add")
    void addBidList(@RequestBody BidList bidList);

    @GetMapping("/bidList/get/{id}")
    BidList getBidList(@PathVariable("id") Integer id);

    @PostMapping("/bidList/update")
    void updateBidList(@RequestBody BidList bidList);

    @GetMapping("/bidList/delete/{id}")
    void deleteBidList(@PathVariable("id") Integer id);
}