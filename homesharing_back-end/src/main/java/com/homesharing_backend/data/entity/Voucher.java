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
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private Host host;

    @Column(name = "name_voucher")
    private String nameVoucher;

    @Column(name = "description")
    private String description;

    @Column(name = "percent")
    private int percent;

    @Column(name = "due_day")
    private int dueDay;

    @Column(name = "status")
    private int status;
}
