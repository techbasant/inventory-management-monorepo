package com.tatamotors.dealers.circuitbreaker;


import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CircuitBreaker {

	@Value("${circuitbreaker.failure-threashold}")
	private int failureThreadshold;
	@Value("${circuitbreaker.open-state-in-millis}")
	private int openStateInMilliseconds;
	
	private int failureCount;
	private long openStateInterval = -1;
	private boolean openState = false;
	
	public Object invoke(Invocation invocation) throws Throwable {
		try {
			
			//check if the circuit is open if circuit is open we can't pass through
			if(openState == true) {
				//get the current time
				Calendar calendar = new GregorianCalendar();
				long ctMilliSeconds = calendar.getTimeInMillis();
				//check if the circuit is still open by comparing the interval
				if((ctMilliSeconds - openStateInterval) < openStateInMilliseconds) {
					throw new CircuitOpenedException("remote service is non-responsive, so circuit got opened");
				}else {
					//if the interval is passed repair the circuit
					openState = false;
					failureCount = 0;
					openStateInterval = -1;
				}
			}
			
			//check if the circuit is closed and is server is unresponsive
			if(failureCount == failureThreadshold) {
				//break the circuit
				openState = true;
				//capture the current time
				Calendar calendar = new GregorianCalendar();
				openStateInterval = calendar.getTimeInMillis();
				throw new CircuitOpenedException("remote service is non-responsive, so circuit got opened");
			}else {
				Object response = invocation.invoke();
				failureCount = 0;
				openState = false;
				return response;
			}
		} catch (Throwable t) {
			//if any exception increase the failure count
			failureCount++;
			throw t;
		}
		
		
	}
	
}
