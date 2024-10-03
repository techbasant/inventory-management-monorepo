package com.tatamotors.dealers.dto;

import lombok.Data;

@Data
public class StockDto {
	protected int stockNo;
	protected String stockName;
	protected int quantity;
	protected double unitPrice;
}
