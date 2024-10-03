package com.aps.api.dto;
import lombok.Data;

@Data
public class WorkOrderRequest {
	private String partnerName;
	private String itemNo;
	private int quantity;
}