package com.app.portfolio.database.DatabaseAndSpringBoot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rentalOffice", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Car> cars = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rentalOffice", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Department> departments = new HashSet<>();

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void addCar(Car car) {
        this.cars.add(car);
        car.setRentalOffice(this);
    }

    public void deleteCar(Car car) {
        this.cars.remove(car);
        car.removeRentalOffice();
    }

    public void addDepartment(Department department) {
        this.departments.add(department);
        department.setRentalOffice(this);
    }

    public void deleteDepartment(Department department) {
         this.departments.remove(department);
         department.deleteRentalOffice();
    }

    public RentalOffice(String name, String city, String street, String postalCode) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }
}
