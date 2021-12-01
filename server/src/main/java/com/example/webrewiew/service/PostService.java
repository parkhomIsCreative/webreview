package com.example.webrewiew.service;


import com.example.webrewiew.dto.PostDTO;
import com.example.webrewiew.entity.ImageModel;
import com.example.webrewiew.entity.Post;
import com.example.webrewiew.entity.User;
import com.example.webrewiew.exceptions.PostNotFoundException;
import com.example.webrewiew.repository.ImageRepository;
import com.example.webrewiew.repository.PostRepository;
import com.example.webrewiew.repository.UserRepository;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    public static final Logger LOG = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public Post createPost(PostDTO postDTO, Principal principal) {
        User user = GetUserByPrincipal.get(userRepository, principal);

        MapperFactory factory = new DefaultMapperFactory.Builder().build();
        factory.classMap(PostDTO.class,Post.class);
        MapperFacade mapper = factory.getMapperFacade();
        Post post = mapper.map(postDTO,Post.class);
        post.setUser(user);
        post.setLikes(0);

        LOG.info("Saving post for user {}", user.getUsername());
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreateDateDesc();
    }

    public Post getPostById(Long postId, Principal principal) {
        User user = GetUserByPrincipal.get(userRepository, principal);
        return postRepository.findPostByIdAndUser(postId, user)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found for username " + user.getUsername()));
    }

    public List<Post> getAllPostsForUser(Principal principal) {
        User user = GetUserByPrincipal.get(userRepository, principal);
        return postRepository.findAllByUserOrderByCreateDateDesc(user);
    }

    public Post likePost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found"));

        Optional<String> userLiked = post.getLikedUsers()
                .stream()
                .filter(u -> u.equals(username))
                .findAny();

        if (userLiked.isPresent()) {
            post.setLikes(post.getLikes() - 1);
            post.getLikedUsers().remove(username);
        } else {
            post.setLikes(post.getLikes() + 1);
            post.getLikedUsers().add(username);
        }

        return postRepository.save(post);
    }

    public void deletePost(Long postId, Principal principal)
    {
        Post post = getPostById(postId,principal);
        Optional<ImageModel> imageModel = imageRepository.findByPostid(post.getId());
        postRepository.delete(post);
        imageModel.ifPresent(imageRepository :: delete);
    }



}
