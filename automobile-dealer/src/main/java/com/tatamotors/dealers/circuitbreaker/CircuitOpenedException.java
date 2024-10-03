package com.tatamotors.dealers.circuitbreaker;

public class CircuitOpenedException extends Throwable{

	private static final long serialVersionUID = 1946606341454335985L;

	public CircuitOpenedException(String message, Throwable cause) {
		super(message, cause);
	}

	public CircuitOpenedException(String message) {
		super(message);
	}

}
