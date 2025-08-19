package com.vakya.comment_service.service;

import com.vakya.comment_service.feign.CommentInterface;
import com.vakya.comment_service.feign.UserClient;
import com.vakya.comment_service.model.Comment;
import com.vakya.comment_service.repository.CommentRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentInterface commentInterface;
    private final UserClient userClient;

    public CommentService(CommentRepository commentRepository,
                          CommentInterface commentInterface,
                          UserClient userClient) {
        this.commentRepository = commentRepository;
        this.commentInterface = commentInterface;
        this.userClient = userClient;
    }

   /* public Comment saveComment(Long postId, String token, String email, String text) {
        String username = validateToken(token);

        try {
            commentInterface.checkPostExists(postId);
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Post with ID " + postId + " does not exist");
        }

        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setName(username);
        comment.setEmail(email);
        comment.setComment(text);

        return commentRepository.save(comment);
    }*/

    public Comment saveComment(Long postId, String token, String email, String text) {
        String username = validateToken(token);

        try {
            commentInterface.checkPostExists(postId);
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Post with ID " + postId + " does not exist");
        }

        //Comment comment = new Comment(postId, username, email, text);
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setName(username);
        comment.setEmail(email);
        comment.setComment(text);
        Comment savedComment = commentRepository.save(comment);

        commentInterface.addCommentToPost(postId, savedComment.getId());

        return savedComment;
    }



    public Comment updateComment(Long commentId, String name, String email, String text, Long postId, String token) {
        String username = validateToken(token);
        verifyOwnership(commentId, username);

        commentInterface.checkPostExists(postId);

        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.setPostId(postId);
        comment.setName(username);
        comment.setEmail(email);
        comment.setComment(text);

        return commentRepository.save(comment);
    }

    public void deleteCommentById(Long commentId, String token) {
        String username = validateToken(token);
        verifyOwnership(commentId, username);

        commentRepository.deleteById(commentId);
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    private String validateToken(String token) {
        try {
            String header = token != null && token.startsWith("Bearer ") ? token : ("Bearer " + token);
            String username = userClient.validateToken(header);
            return username;
        } catch (FeignException.Unauthorized e) {
            throw new RuntimeException("Invalid or expired token");
        } catch (Exception e) {
            throw new RuntimeException("Token validation failed: " + e.getMessage());
        }
    }

    private void verifyOwnership(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (comment.getName() == null || !comment.getName().equals(username)) {
            throw new RuntimeException("You are not authorized to modify this comment");
        }
    }

}
