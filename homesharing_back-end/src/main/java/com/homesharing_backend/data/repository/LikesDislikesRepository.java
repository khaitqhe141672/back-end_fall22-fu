package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.LikesDislikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesDislikesRepository extends JpaRepository<LikesDislikes, Long> {

    LikesDislikes getLikesDislikesById(Long id);

    @Query(value = "SELECT count(l.id) FROM LikesDislikes l WHERE l.rate.id= :rateID AND l.type= :type", nativeQuery = true)
    int countLikeOrDislike(Long rateID, int type);

    int countLikesDislikesByRate_IdAndType(Long rateID, int type);

    int countLikesDislikesByRate_IdAndTypeAndStatus(Long rateID, int type, int status);

    LikesDislikes getLikesDislikesByCustomer_IdAndRate_Id(Long customerID, Long rateID);
}
