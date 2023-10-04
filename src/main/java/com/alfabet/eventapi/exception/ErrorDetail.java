package com.alfabet.eventapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorDetail<T> {
	private T id;
	private String errorMessage;
	private String errorCode; // If you want to provide error codes

	public ErrorDetail(T id, String errorMessage) {
		this.id = id;
		this.errorMessage = errorMessage;
	}

}