package com.homesharing_backend.data.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_detail_id")
    private Long userDetailId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "address")
    private String address;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "dob")
    private Date dob;

}
