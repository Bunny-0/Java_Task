package org.example;

import org.example.config.AuthnServiceOutboundCommunication;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class MedilabAuthGateway implements GlobalFilter {

    @Autowired
    AuthnServiceOutboundCommunication authnService;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();

        if (headers.containsKey("Authorization")) {
            String headerValue = headers.getFirst("Authorization");
            if (headerValue != null && headerValue.startsWith("Bearer ")) {
                String accessToken = headerValue.substring(7); // Remove "Bearer "
                String userResp = authnService.getUserInfo(accessToken);
                if (userResp != null) {
                    JSONObject jsonResp = new JSONObject(userResp);
                    String loggedInUser = jsonResp.getString("sub");
                    HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
                    String tokenFromCache = hashOps.get("access_token:" + loggedInUser, "Bearer");

                    if (tokenFromCache != null && tokenFromCache.equals(accessToken)) {
                        // Token verification success
                        String authorities = jsonResp.getString("groups");
                        if (authorities != null) {
                            hashOps.put(accessToken, "userRoles", authorities);
                        }
                        return chain.filter(exchange); // Proceed to the next filter
                    } else {
                        return onError(exchange, "Token verification failed", HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        return onError(exchange, "Authorization header is missing or invalid", HttpStatus.FORBIDDEN);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        // Optionally add error message to response
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
        return response.writeWith(Mono.just(response.bufferFactory().wrap(err.getBytes())));
    }

    }
