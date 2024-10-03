package com.tatamotors.dealers.aspect;

import java.util.List;

// import javax.annotation.PostConstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import com.tatamotors.dealers.discovery.manager.InventoryMgmtServiceDiscoveryManager;

@Component
@Aspect
public class RoundRobinLBRAroundAdvice {

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
	
	@Pointcut("@annotation(com.tatamotors.dealers.annotation.ClientSideRoundRobinLoadBalancer)")
	public void loadbalancerAnnotationPointcut() {
		
	}
	
	
	@Around("loadbalancerAnnotationPointcut()")
	public Object invoke(ProceedingJoinPoint invocation) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("Around advice invoked");
		Object[] args = invocation.getArgs();
		ServiceInstance serviceInstance = null;
		serviceInstance = fetchInstancesAndReturn();
		String uri = (String) args[1];
		uri = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort();
		args[1] = uri;
		return invocation.proceed(args);
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
