package com.tatamotors.dealers.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tatamotors.dealers.dto.StockDto;


@Service
@FeignClient(name = "INVENTORY-MGMT-SERVICE", path = "/inventory/stock")
public interface StockService {

	@GetMapping(value = "/{stockName}/available", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<StockDto> getStocks(@PathVariable("stockName") String stockName) ;
}
