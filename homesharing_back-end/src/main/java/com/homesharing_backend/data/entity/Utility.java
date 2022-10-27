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
public class Utility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "icon")
    private String icon;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private Host host;
}
