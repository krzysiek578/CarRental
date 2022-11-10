package com.app.portfolio.database.DatabaseAndSpringBoot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private List<Car> cars;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rentalOffice", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Department> departments;

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
        if(cars == null) {
            this.cars = new ArrayList<>();
            this.cars.add(car);
            return;
        }
        this.cars.add(car);
    }

    public boolean deleteCar(Car car) {
        if(cars != null) {
            return this.cars.remove(car);
        }
        return false;
    }

    public void addDepartment(Department department) {
        if(this.departments == null) {
            this.departments = new ArrayList<>();
            this.departments.add(department);
            return;
        }
        this.departments.add(department);
    }

    public boolean deleteDepartment(Department department) {
        if(department != null) {
            return this.departments.remove(department);
        }
        return false;
    }

    public RentalOffice(String name, String city, String street, String postalCode) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }
}
