package com.example.webrewiew.service;

import com.cloudinary.Cloudinary;
import com.example.webrewiew.entity.ImageModel;
import com.example.webrewiew.entity.Post;
import com.example.webrewiew.entity.User;
import com.example.webrewiew.exceptions.ImageNotFoundException;
import com.example.webrewiew.repository.ImageRepository;
import com.example.webrewiew.repository.PostRepository;
import com.example.webrewiew.repository.UserRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;



@Data
@Service
public class ImageService {
    public static final Logger LOG = LoggerFactory.getLogger(ImageService.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Autowired
    Cloudinary cloudinaryInit;



    @Autowired
    public ImageService(PostRepository postRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public ImageModel uploadImageToUser(MultipartFile file, Principal principal) throws IOException {
        User user = GetUserByPrincipal.get(userRepository, principal);
        LOG.info("Uploading image profile to user {}", user.getUsername());

        ImageModel userProfileImage = imageRepository.findByUserid(user.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(userProfileImage)) {
            imageRepository.delete(userProfileImage);
        }

        CloudinaryUpload cloudinaryUpload = new CloudinaryUpload();
        ImageModel imageModel = new ImageModel();
        imageModel.setUserid(user.getId());
        imageModel.setImageURL(uploadFile(file));
        imageModel.setName(file.getOriginalFilename());
        return imageRepository.save(imageModel);
    }


        public ImageModel uploadImageToPost (MultipartFile file, Principal principal, Long postId) throws IOException {
            User user = GetUserByPrincipal.get(userRepository, principal);
            Post post = user.getPosts()
                    .stream()
                    .filter(p -> p.getId().equals(postId))
                    .collect(toSinglePostCollector());

            CloudinaryUpload cloudinaryUpload = new CloudinaryUpload();
            ImageModel imageModel = new ImageModel();
            imageModel.setPostid(post.getId());
            imageModel.setImageURL(uploadFile(file));
            imageModel.setName(file.getOriginalFilename());
            LOG.info("Uploading image to Post {}", post.getId());

            return imageRepository.save(imageModel);
        }


        public ImageModel getImageToUser (Principal principal){
            User user = GetUserByPrincipal.get(userRepository, principal);

            ImageModel imageModel = imageRepository.findByUserid(user.getId()).orElse(null);

            return imageModel;
        }

        public ImageModel getImageToPost (Long postId){
            Post post = postRepository.findById(postId).orElse(null);

            ImageModel imageModel = imageRepository.findByPostid(postId)
                    .orElseThrow(() -> new ImageNotFoundException("Cannot be found image to post " + postId));
            return imageModel;
        }

        private <T > Collector < T,?,T > toSinglePostCollector()
        {
            return Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> {
                        if (list.size() != 1) {
                            throw new IllegalStateException();
                        }
                        return list.get(0);
                    });
        }
    public String uploadFile(MultipartFile file) {
        try {

            Map uploadResult = cloudinaryInit.uploader().upload(file.getBytes(), com.cloudinary.utils.ObjectUtils.emptyMap());
            return  uploadResult.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }







    }

