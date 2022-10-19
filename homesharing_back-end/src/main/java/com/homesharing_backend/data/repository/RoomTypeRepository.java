package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    Optional<RoomType> findRoomTypeById(Long id);
}
