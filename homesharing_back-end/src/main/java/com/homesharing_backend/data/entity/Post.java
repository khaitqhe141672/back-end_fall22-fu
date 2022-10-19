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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private Host host;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private float price;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "status")
    private int status;

}
