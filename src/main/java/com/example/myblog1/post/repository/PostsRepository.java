package com.example.myblog1.post.repository;

import com.example.myblog1.post.entity.Posts;
import com.example.myblog1.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts,Long> {

    Page<Posts> findAllById(Long id, Pageable pageableSetting);

    Optional<Posts> findByIdAndUserId(Long id, Long userId);
}
