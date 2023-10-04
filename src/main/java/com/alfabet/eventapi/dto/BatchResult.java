package com.alfabet.eventapi.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.alfabet.eventapi.exception.ErrorDetail;
import com.alfabet.eventapi.model.Subscriber;
import com.alfabet.eventapi.model.Tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BatchResult<T> {

	private List<T> successfulOps;
	private List<ErrorDetail<Long>> failedOps;

	public List<T> getSuccessfulOps() {
		return successfulOps;
	}

	public void setSuccessfulOps(List<T> successfulOps) {
		this.successfulOps = successfulOps;
	}

	public List<ErrorDetail<Long>> getFailedOps() {
		return failedOps;
	}

	public void setFailedOps(List<ErrorDetail<Long>> failedOps) {
		this.failedOps = failedOps;
	}
}