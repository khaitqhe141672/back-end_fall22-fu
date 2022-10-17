package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM Post order by id DESC LIMIT 5", nativeQuery = true)
    List<Post> getPostTop();

    Post getPostById(Long id);

    Boolean existsPostById(Long id);
}
