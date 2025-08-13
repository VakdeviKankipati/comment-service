package com.vakya.comment_service.repository;

import com.vakya.comment_service.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getCommentById(Long commentId);
}
