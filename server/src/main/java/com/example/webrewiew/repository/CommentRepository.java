package com.example.webrewiew.repository;

import com.example.webrewiew.entity.Comment;
import com.example.webrewiew.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findAllByPost(Post post);

    List<Comment> findByIdAndUserid (Long commentId, Long userId);
}
