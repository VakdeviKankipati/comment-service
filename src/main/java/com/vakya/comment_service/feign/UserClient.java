package com.vakya.comment_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {
    @GetMapping("/users/validate-token")
    String validateToken(@RequestHeader("Authorization") String token);
}

