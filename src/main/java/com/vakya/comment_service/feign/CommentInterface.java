package com.vakya.comment_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "POST-SERVICE")
public interface CommentInterface {
    @GetMapping("/posts/{postId}")
    void checkPostExists(@PathVariable("postId") Long postId);
}
