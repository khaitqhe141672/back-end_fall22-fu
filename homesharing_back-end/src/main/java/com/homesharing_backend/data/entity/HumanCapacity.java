package com.homesharing_backend.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "human_capacity")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HumanCapacity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}
