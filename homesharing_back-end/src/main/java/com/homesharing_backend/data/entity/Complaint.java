package com.homesharing_backend.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    private Report report;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private int status;

}
