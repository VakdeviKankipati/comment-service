package com.vakya.comment_service.controller;

import com.vakya.comment_service.model.Comment;
import com.vakya.comment_service.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment/{postId}")
    public Comment createComment(@PathVariable Long postId, @RequestBody Comment comment,
                                 @RequestHeader("Authorization") String token) {

        return commentService.saveComment(postId, token, comment.getEmail(), comment.getComment());
    }


    @GetMapping("/comment/{commentId}")
    public Comment getCommentById(@PathVariable Long commentId){
        return commentService.getCommentById(commentId);
    }

    @PutMapping("update/{commentId}")
    public Comment updateCommentToPost(
            @PathVariable Long commentId,
            @RequestParam Long postId,
            @RequestBody Comment comment,
            @RequestHeader("Authorization") String token) {

        return commentService.updateComment(
                commentId,
                comment.getName(),
                comment.getEmail(),
                comment.getComment(),
                postId,
                token
        );
    }

    @DeleteMapping("delete/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId,
            @RequestHeader("Authorization") String token) {

        commentService.deleteCommentById(commentId, token);
        return ResponseEntity.ok("Comment deleted successfully");
    }

}
