package com.ims.dto;

import lombok.Data;

@Data
public class StockDto {
	private int stockNo;
	private String stockName;
	private int quantity;
	private double unitPrice;
}
