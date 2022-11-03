package com.app.portfolio.database.DatabaseAndSpringBoot;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "areas")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;

    public Area(String name) {
        this.name = name;
    }
}
