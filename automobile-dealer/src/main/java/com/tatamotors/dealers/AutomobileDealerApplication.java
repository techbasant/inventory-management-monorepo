package com.tatamotors.dealers;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

import com.tatamotors.dealers.circuitbreaker.resilience4j.StockeClintResilience4j;
import com.tatamotors.dealers.dto.StockDto;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@EnableFeignClients
public class AutomobileDealerApplication {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	Customizer<Resilience4JCircuitBreakerFactory> slowCircuitBreakerCustomizer() {
		
		return (tocustomize) -> {
			tocustomize.configure((builder) -> {
				builder.circuitBreakerConfig(CircuitBreakerConfig.custom().failureRateThreshold(5)
						.waitDurationInOpenState(Duration.ofSeconds(3)).slidingWindowSize(2).build())
						.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(3)).build());
			}, "slow");
		};
	}
	
	public static void main(String[] args) throws Throwable {
		SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder(AutomobileDealerApplication.class).web(WebApplicationType.NONE);
		SpringApplication springApplication = applicationBuilder.build();
		ApplicationContext context = springApplication.run(args);
//		StockApiService stockService = context.getBean(StockApiService.class);
		
//		StockService stockService = context.getBean(StockService.class);
//		StockClient stockService = context.getBean(StockClient.class);
		 StockeClintResilience4j stockService = context.getBean(StockeClintResilience4j.class);
		for (int i = 0; i < 12; i++) {
			List<StockDto> stocks = stockService.getStocks("spark");
			stocks.forEach(System.out::println);
			Thread.sleep(100);
		}
	}

}
