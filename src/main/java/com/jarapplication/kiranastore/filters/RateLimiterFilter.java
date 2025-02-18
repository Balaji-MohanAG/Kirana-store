package com.jarapplication.kiranastore.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import java.io.IOException;
import java.time.Duration;

@Component
public class RateLimiterFilter extends OncePerRequestFilter {


    Bucket bucket = this.createNewBucket();

    /**
     * Creates a Bucket with tokens for 100 per minutes
     * @return Bucket
     */
    private Bucket createNewBucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1))))
                .build();
    }

    /**
     * Adds a filter for rate limiting
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if(request.getServletPath().startsWith("/actuators/**")){
            filterChain.doFilter(request, response);
            return;
        }
        if (!bucket.tryConsume(1)) {
            response.setStatus(429);
            response.getWriter().write("Too Many Requests");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
