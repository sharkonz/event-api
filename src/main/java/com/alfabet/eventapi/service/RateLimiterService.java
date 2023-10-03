package com.alfabet.eventapi.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;

@Service
public class RateLimiterService {

	private Bucket bucket;

	public RateLimiterService() {
		resetBucket();
	}

	public void resetBucket() {
		this.bucket = Bucket4j.builder().addLimit(Bandwidth.simple(10, Duration.ofMinutes(1))).build();
	}

	public boolean tryConsume() {
		return bucket.tryConsume(1);
	}
}
