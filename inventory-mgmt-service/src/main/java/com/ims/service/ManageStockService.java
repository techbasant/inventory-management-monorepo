package com.ims.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ims.dto.StockDto;
import com.ims.entities.Stock;
import com.ims.repositories.StockRepository;


@Service
@ConfigurationProperties(prefix = "stock")
public class ManageStockService {
	private String transportType;
	private int slaDays;
	@Autowired
	private StockRepository stockRepository;

	@Transactional(readOnly = true)
	public List<StockDto> getStocks(String stockName) {
		return stockRepository.findByStockNameLike("%" + stockName + "%").stream().map(e -> {
			StockDto dto = new StockDto();
			copyProperties(e, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	private void copyProperties(Stock e, StockDto dto) {
		dto.setStockNo(e.getStockNo());
		dto.setStockName(e.getStockName());
		dto.setQuantity(e.getQuantity());
		dto.setUnitPrice(e.getUnitPrice());
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public void setSlaDays(int slaDays) {
		this.slaDays = slaDays;
	}

	public void setStockRepository(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	public String getTransportType() {
		return transportType;
	}

	public int getSlaDays() {
		return slaDays;
	}
}
