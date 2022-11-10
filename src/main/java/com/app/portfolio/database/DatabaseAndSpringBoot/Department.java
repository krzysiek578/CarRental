package com.app.portfolio.database.DatabaseAndSpringBoot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "departments")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private RentalOffice rentalOffice;

    public Department(String name) {
        this.name = name;
    }

    public void addRentalOffice(RentalOffice rentalOffice) {
        this.rentalOffice = rentalOffice;
    }

    public void deleteRentalOffice() {
        this.rentalOffice = null;
    }

}
