package com.alfabet.eventapi.integration.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alfabet.eventapi.service.RateLimiterService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RateLimiterTest {

    @Autowired
    private RateLimiterService rateLimiterService;
    
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void resetRateLimiter() {
        rateLimiterService.resetBucket();
    }
    
    @Test
    public void testRateLimiting() throws Exception {
        // Assuming you've set a limit of 10 requests per minute
        for (int i = 0; i < 10; i++) {
            mockMvc.perform(MockMvcRequestBuilders.get("/events/events/getallevents"))
                .andExpect(status().isOk());
        }
        // The 6th request should fail due to rate limiting
        mockMvc.perform(MockMvcRequestBuilders.get("/events/events/getallevents"))
            .andExpect(status().is(HttpStatus.TOO_MANY_REQUESTS.value()));
    }
}
