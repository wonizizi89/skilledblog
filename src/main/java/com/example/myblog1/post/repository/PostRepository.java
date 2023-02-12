package com.example.myblog1.post.repository;

import com.example.myblog1.post.entity.Post;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  Optional<Post> findByIdAndUserId(Long id, Long userId);

  Page<Post> findAllByTitleContainingOrContentContaining(String title, String content,
      PageRequest of);
  
//    @Query(
//           value = "SELECT p FROM Post p WHERE p.title LIKE %:title% OR p.content LIKE %:content%"
//    )

}
