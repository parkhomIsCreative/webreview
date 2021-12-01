package com.example.webrewiew.service;

import com.example.webrewiew.dto.CommentDTO;
import com.example.webrewiew.entity.Comment;
import com.example.webrewiew.entity.Post;
import com.example.webrewiew.entity.User;
import com.example.webrewiew.exceptions.PostNotFoundException;
import com.example.webrewiew.repository.CommentRepository;
import com.example.webrewiew.repository.PostRepository;
import com.example.webrewiew.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    public static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment saveComment(Long postId, CommentDTO commentDTO, Principal principal) {
        User user = GetUserByPrincipal.get(userRepository, principal);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found for username: " + user.getUsername()));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUserid(user.getId());
        comment.setUsername(user.getUsername());
        comment.setMessage(commentDTO.getMessage());

        LOG.info("Saving comment for Post: {}", post.getId());
        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommentsForPost(Long postId)
    {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found"));
        List<Comment> comments = commentRepository.findAllByPost(post);

        return comments;
    }

    public void deleteComment(Long commentId)
    {
        Optional<Comment> comment = commentRepository.findById(commentId);
        comment.ifPresent(commentRepository :: delete);
    }
}
