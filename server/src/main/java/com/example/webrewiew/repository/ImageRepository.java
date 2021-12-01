package com.example.webrewiew.repository;

import com.example.webrewiew.entity.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel, Long> {

    Optional<ImageModel> findByUserid (Long userId);
    Optional<ImageModel> findByPostid (Long postId);

}
