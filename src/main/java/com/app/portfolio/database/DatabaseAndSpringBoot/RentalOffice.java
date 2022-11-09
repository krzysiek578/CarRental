package com.app.portfolio.database.DatabaseAndSpringBoot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name = "rentalOffices")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RentalOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String city;
    private String street;
    private String postalCode;

    public RentalOffice(String name, String city, String street, String postalCode) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }
}
