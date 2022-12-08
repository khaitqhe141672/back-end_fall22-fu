package com.homesharing_backend.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "report_post")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ReportPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_type_id", referencedColumnName = "id")
    private ReportType reportType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking booking;

    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "status")
    private int status;
}
