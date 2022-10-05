package com.homesharing_backend.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "post_detail")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class PostDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @Column(name = "address")
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private District district;

    @Column(name = "description")
    private String description;

    @Column(name = "service_fee")
    private float serviceFee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_type_id", referencedColumnName = "id")
    private RoomType roomType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "human_capacity_id", referencedColumnName = "id")
    private HumanCapacity humanCapacity;

    @Column(name = "guest_number")
    private int guestNumber;

    @Column(name = "number_of_beds")
    private int numberOfBeds;

    @Column(name = "number_of_bedrooms")
    private int numberOfBedrooms;

    @Column(name = "numberOfBathrooms")
    private int numberOfBathroom;
}
