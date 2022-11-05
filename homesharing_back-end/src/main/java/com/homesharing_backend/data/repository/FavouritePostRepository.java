package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.FavouritePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouritePostRepository extends JpaRepository<FavouritePost, Long> {

    FavouritePost getFavouritePostById(Long id);

    int countFavouritePostByPost_Id(Long postID);
}
