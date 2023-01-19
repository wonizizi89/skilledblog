package com.example.myblog1.post.repository;

import com.example.myblog1.post.entity.Posts;
import com.example.myblog1.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostsRepository extends JpaRepository<Posts,Long> {

    Optional<Posts> findByIdAndUserId(Long id, Long userId);

    @Query(
           value = "SELECT p FROM Posts p WHERE p.title LIKE %:title% OR p.content LIKE %:content%"
    )
    Page<Posts> findAllSearch(String title,String content,Pageable pageChoice);
}
