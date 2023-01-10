package com.example.myblog1.post.repository;

import com.example.myblog1.post.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {

    List<Posts> findAllByOrderByModifiedAtDesc();


}
