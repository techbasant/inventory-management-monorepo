package com.tatamotors.dealers.circuitbreaker.resilience4j;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;

import com.tatamotors.dealers.dto.StockDto;
import com.tatamotors.dealers.feign.StockService;

@Component
public class StockeClintResilience4j {

	@Autowired
	private StockService stockService;
	
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;
	
	public List<StockDto> getStocks(String stockName) {
		
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("slow");
		return circuitBreaker.run(new Supplier<List<StockDto>>() {

			@Override
			public List<StockDto> get() {
				// TODO Auto-generated method stub
				return stockService.getStocks(stockName);
			}
		});
		
	}
}
