package com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice;

import com.app.portfolio.database.DatabaseAndSpringBoot.car.Car;
import com.app.portfolio.database.DatabaseAndSpringBoot.deprtment.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import java.util.HashSet;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private String street;
    private String postalCode;

    @OneToMany(mappedBy = "rentalOffice", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Car> cars = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rentalOffice", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Department> departments = new HashSet<>();


    public RentalOffice(String name, String city, String street, String postalCode) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public void addDepartment(Department department) {
        this.departments.add(department);
        department.setRentalOffice(this);
    }

    public void removeDepartment(Department department) {
        this.departments.remove(department);
        department.setRentalOffice(null);
    }

    public void addCar(Car car) {
        this.cars.add(car);
        car.setRentalOffice(this);
    }

    public void removeCar(Car car) {
        this.cars.remove(car);
        car.setRentalOffice(null);
    }
}
