package com.ims.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "stock")
@Data
public class Stock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stock_no")
	protected int stockNo;
	@Column(name = "stock_nm")
	protected String stockName;
	protected String description;
	protected int quantity;
	@Column(name = "unit_price")
	protected double unitPrice;
	
	
}
