package com.homesharing_backend.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "booking_utility")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingUtility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking booking;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_utility_id", referencedColumnName = "id")
    private PostUtility postUtility;
}
