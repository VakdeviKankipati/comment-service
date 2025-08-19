package com.vakya.comment_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "POST-SERVICE", url = "${POST_SERVICE_URL}")
public interface CommentInterface {
    @GetMapping("/posts/{postId}")
    void checkPostExists(@PathVariable("postId") Long postId);

    @PostMapping("/posts/{postId}/add-comment/{commentId}")
    void addCommentToPost(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId);
}
