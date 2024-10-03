package com.ims.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.dto.StockDto;
import com.ims.service.ManageStockService;

@RestController
@RequestMapping("/stock")
public class StockApiController {
	@Autowired
	private ManageStockService manageStockService;

	@GetMapping(value = "/{stockName}/available", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<StockDto> getStocks(@PathVariable("stockName") String stockName) {
		System.out.println("invoked stock api service code : " + this.hashCode());
		sleep();
		return manageStockService.getStocks(stockName);
	}

	private void sleep() {
		try {
			Thread.sleep(1000 * 2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@GetMapping(value = "/transport", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String getTranportInfo() throws JsonProcessingException {
		Map<String, Object> dataMap = null;
		ObjectMapper objectMapper = null;
		String jsonResponse = null;

		dataMap = new HashMap<String, Object>();
		dataMap.put("transportType", manageStockService.getTransportType());
		dataMap.put("slaDays", manageStockService.getSlaDays());
		objectMapper = new ObjectMapper();
		jsonResponse = objectMapper.writeValueAsString(dataMap);
		return jsonResponse;
	}
}
