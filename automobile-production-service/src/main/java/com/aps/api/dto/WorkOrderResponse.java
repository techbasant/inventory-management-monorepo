package com.aps.api.dto;
import java.util.Date;

import lombok.Data;

@Data
public class WorkOrderResponse {
	private String workorderNo;
	private Date slaDate;
	private String status;
}