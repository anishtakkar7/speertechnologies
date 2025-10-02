package com.speertechnologies.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RateLimitingFilter extends OncePerRequestFilter {

    private final RateLimiter rateLimiter;

    public RateLimitingFilter(double requestsPerSecond) {
        // Configure the rate limiter to allow a specific number of requests per second
        this.rateLimiter = RateLimiter.create(requestsPerSecond);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Try to acquire a permit from the rate limiter
        if (rateLimiter.tryAcquire()) {
            filterChain.doFilter(request, response); // Allow the request to proceed
        } else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // 429 status code
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\": \"Too many requests\", \"message\": \"Please try again later.\"}");
           // response.getWriter().write("Too many requests");
        }
    }
}

