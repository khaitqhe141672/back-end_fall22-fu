package com.homesharing_backend.data.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Column(name = "total_money")
    private float totalMoney;

    @Column(name = "note")
    private String note;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "status")
    private int status;

}
