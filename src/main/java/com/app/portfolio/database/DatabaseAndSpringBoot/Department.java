package com.app.portfolio.database.DatabaseAndSpringBoot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "department_cars",
            joinColumns = { @JoinColumn(name = "fk_department") },
            inverseJoinColumns = { @JoinColumn(name = "fk_cars") })
    //Właściciel relacji jointable
    //joincolumn do jednokierunkowej relacji chyba ze masz wymaganie nazwania klucza obcego
    //a mappedby do dwukierunkowych
    private Set<Car> carSet = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Area area;


    public Department(String name) {
        this.name = name;
    }

    public void addRentalOffice(RentalOffice rentalOffice) {
        this.rentalOffice = rentalOffice;
    }

    public void deleteRentalOffice() {
        this.rentalOffice = null;
    }

    public void addCar(Car car) {
        this.carSet.add(car);
        car.addDepartment(this);
    }

    public void removeCar(Car car) {
        this.carSet.remove(car);
        car.getDepartmentsSet().remove(this);
    }

    public void addArea(Area area) {
        this.area = area;
        area.getDepartments().add(this);
    }

    public void removeArea() {
        area.getDepartments().remove(this);
        this.area = null;
    }
}
