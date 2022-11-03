package com.app.portfolio.database.DatabaseAndSpringBoot;

import lombok.*;

import javax.persistence.*;

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
