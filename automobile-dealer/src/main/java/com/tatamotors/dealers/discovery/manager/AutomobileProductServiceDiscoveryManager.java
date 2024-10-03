package com.tatamotors.dealers.discovery.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

@Component
public class AutomobileProductServiceDiscoveryManager {
	private final String PRODUCT_SERVICE_ID = "AUTOMOBILE-PRODUCTION-SERVICE";
	@Autowired
	private DiscoveryClient discoveryClient;

	public List<ServiceInstance> getAutomobileProductionServiceInstances() {
		return discoveryClient.getInstances(PRODUCT_SERVICE_ID);
	}
}
