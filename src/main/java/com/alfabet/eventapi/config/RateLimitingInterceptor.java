package com.alfabet.eventapi.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alfabet.eventapi.service.RateLimiterService;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

	@Autowired
	private RateLimiterService rateLimiterService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!rateLimiterService.tryConsume()) {
			response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // HTTP 429 Too Many Requests
			response.getWriter().write("Too many requests. Please try again later.");
			return false;
		}
		return true;
	}
}