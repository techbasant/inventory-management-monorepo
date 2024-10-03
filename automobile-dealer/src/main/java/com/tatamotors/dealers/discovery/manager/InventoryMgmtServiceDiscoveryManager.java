package com.tatamotors.dealers.discovery.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

@Component
public class InventoryMgmtServiceDiscoveryManager {
	private final String STOCK_API_NAME = "INVENTORY-MGMT-SERVICE";

	@Autowired
	private DiscoveryClient discoveryClient;

	public List<ServiceInstance> getStockApiInstances() {
		return discoveryClient.getInstances(STOCK_API_NAME);
	}
}
