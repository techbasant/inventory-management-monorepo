package com.tatamotors.dealers.aspect;


import java.util.List;

// import javax.annotation.PostConstruct;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import com.tatamotors.dealers.discovery.manager.InventoryMgmtServiceDiscoveryManager;

//@Component
@Aspect
public class RoundRobinLBRAdvice {
	
	private int lastUsedInstance= -1;
	
	@Autowired
	private InventoryMgmtServiceDiscoveryManager inventoryMgmtServiceDiscoveryManager;
	

	// @PostConstruct
	// public void init() {
	// 	lastUsedInstance = -1;
	// }
	
	@Pointcut("execution(* com.tatamotors.dealers.service.StockApiService.*(..))")
	public void clientLoadBalancerPointCut() {
		
	}
	
	@Before("clientLoadBalancerPointCut()")
	public void before(JoinPoint joinPoint) throws Throwable {
		String method = joinPoint.getSignature().getDeclaringTypeName(); 
		Object[] args = joinPoint.getArgs();
		Object target = joinPoint.getTarget();
		
		ServiceInstance serviceInstance = null;
		serviceInstance = fetchInstancesAndReturn();
		String uri = (String) args[1];
		uri = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort();
		args[1] = uri;
		System.out.println("invoked before the stock api methode : URI => " + uri);
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

}
