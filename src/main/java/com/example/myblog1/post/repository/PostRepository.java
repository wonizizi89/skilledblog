package com.example.myblog1.post.repository;

import com.example.myblog1.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {

    Optional<Post> findByIdAndUserId(Long id, Long userId);

    @Query(
           value = "SELECT p FROM Post p WHERE p.title LIKE %:title% OR p.content LIKE %:content%"
    )
    Page<Post> findAllSearch(String title, String content, Pageable pageChoice);
}
