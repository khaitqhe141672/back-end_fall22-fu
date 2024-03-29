package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

    UserDetail getUserDetailByUserDetailId(Long userDetailID);
}
