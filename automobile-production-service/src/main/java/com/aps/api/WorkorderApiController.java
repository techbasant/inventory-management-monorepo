package com.aps.api;
import java.util.Date;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aps.api.dto.WorkOrderRequest;
import com.aps.api.dto.WorkOrderResponse;


@RestController
@RequestMapping("/workorder")
public class WorkorderApiController {

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public WorkOrderResponse placeOrder(@RequestBody WorkOrderRequest orderRequest) {
		WorkOrderResponse orderResponse = null;

		orderResponse = new WorkOrderResponse();
		orderResponse.setWorkorderNo(UUID.randomUUID().toString());
		orderResponse.setSlaDate(new Date());
		orderResponse.setStatus("accepted");

		return orderResponse;
	}
}