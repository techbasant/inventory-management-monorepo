package com.tatamotors.dealers.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.tatamotors.dealers.annotation.ClientSideRoundRobinLoadBalancer;
import com.tatamotors.dealers.discovery.manager.InventoryMgmtServiceDiscoveryManager;
import com.tatamotors.dealers.dto.StockDto;

@Service
public class StockApiService {

	private final String STOCK_API_BASE_URI = "/inventory/stock/{stockName}/available";
	private int lastUsedInstance = -1;
	@Autowired
	private InventoryMgmtServiceDiscoveryManager inventoryMgmtServiceDiscoveryManager;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	// @PostConstruct
	// public void init() {
	// 	lastUsedInstance = -1;
	// }
	
	public List<StockDto> getStocks(String stockName) {
		String uri = null;
		ServiceInstance serviceInstance = null;
		Map<String, Object> pathVariables = null;
		List<StockDto> stockDtos = null;

		serviceInstance = fetchInstancesAndReturn();
		uri = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + STOCK_API_BASE_URI;
		pathVariables = new HashMap<String, Object>();
		pathVariables.put("stockName", stockName);

		String url = UriComponentsBuilder.fromUriString(uri).uriVariables(pathVariables).build().toUriString();
		System.out.println(url);

		ResponseEntity<List<StockDto>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<StockDto>>() {
				});
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			stockDtos = responseEntity.getBody();
		}
		return stockDtos;
	}
	
	private ServiceInstance fetchInstancesAndReturn() {
		List<ServiceInstance> stockApiInstances = inventoryMgmtServiceDiscoveryManager.getStockApiInstances();
		if (lastUsedInstance < stockApiInstances.size() - 1) {
			lastUsedInstance++;
		} else {
			lastUsedInstance = 0;
		}
		return stockApiInstances.get(lastUsedInstance);
	}
	
	@ClientSideRoundRobinLoadBalancer
	public List<StockDto> getStocks(String stockName, String uri) {
		System.out.println("invoked after around uri : " + uri);
		Map<String, Object> pathVariables = null;
		List<StockDto> stockDtos = null;

		uri += STOCK_API_BASE_URI;
		pathVariables = new HashMap<String, Object>();
		pathVariables.put("stockName", stockName);

		String url = UriComponentsBuilder.fromUriString(uri).uriVariables(pathVariables).build().toUriString();
		System.out.println(url);

		ResponseEntity<List<StockDto>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<StockDto>>() {
				});
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			stockDtos = responseEntity.getBody();
		}
		return stockDtos;
	}
	
	public List<StockDto> getStockList(String stockName) {
		String uri = "http://" + "INVENTORY-MGMT-SERVICE";
		Map<String, Object> pathVariables = null;
		List<StockDto> stockDtos = null;

		uri += STOCK_API_BASE_URI;
		pathVariables = new HashMap<String, Object>();
		pathVariables.put("stockName", stockName);

		String url = UriComponentsBuilder.fromUriString(uri).uriVariables(pathVariables).build().toUriString();
		System.out.println(url);

		ResponseEntity<List<StockDto>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<StockDto>>() {
				});
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			stockDtos = responseEntity.getBody();
		}
		return stockDtos;
	}

}
