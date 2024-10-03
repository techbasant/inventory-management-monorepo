package com.tatamotors.dealers.circuitbreaker.stockslient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tatamotors.dealers.circuitbreaker.CircuitBreaker;
import com.tatamotors.dealers.circuitbreaker.Invocation;
import com.tatamotors.dealers.dto.StockDto;
import com.tatamotors.dealers.feign.StockService;

@Component
public class StockClient {
	@Autowired
	private CircuitBreaker circuitBreaker;
	
	@Autowired
	private StockService stockService;
	
	@SuppressWarnings("unchecked")
	public List<StockDto> getStocks(String stockName) {
		try {
			return (List<StockDto>) circuitBreaker.invoke(new Invocation() {
				@Override
				public Object invoke() {
					// TODO Auto-generated method stub
					return stockService.getStocks(stockName);
				}
			});
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return List.of(new StockDto());
		
	}
}
