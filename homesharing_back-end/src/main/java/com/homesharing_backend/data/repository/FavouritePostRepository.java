package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.FavouritePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouritePostRepository extends JpaRepository<FavouritePost, Long> {
}
