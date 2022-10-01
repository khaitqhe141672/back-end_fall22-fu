package com.homesharing_backend.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "report_type")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReportType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private int status;
}
