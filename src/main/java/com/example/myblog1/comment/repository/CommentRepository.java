package com.example.myblog1.comment.repository;

import com.example.myblog1.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<Comment> findByIdAndUserId(Long id, Long id1);

}
