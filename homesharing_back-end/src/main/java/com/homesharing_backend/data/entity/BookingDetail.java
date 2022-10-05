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
public class BookingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking booking;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "total_person")
    private int totalPerson;

    @Column(name = "service_charge")
    private float serviceCharge;

    @Column(name = "status")
    private int status;

}
