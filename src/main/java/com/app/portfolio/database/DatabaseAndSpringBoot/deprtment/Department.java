package com.app.portfolio.database.DatabaseAndSpringBoot.deprtment;

import com.app.portfolio.database.DatabaseAndSpringBoot.area.Area;
import com.app.portfolio.database.DatabaseAndSpringBoot.car.Car;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.RentalOffice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "departments")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Set<Car> cars = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Area area;


    public Department(String name) {
        this.name = name;
    }


    public void addCar(Car car) {
        cars.add(car);
        car.getDepartments().add(this);
    }

    public void removeCar(Car car) {
        cars.remove(car);
        car.getDepartments().remove(this);
    }

    public void addArea(Area area) {
        this.setArea(area);
        area.getDepartments().add(this);
    }

    public void removeArea(){
        this.area.getDepartments().remove(this);
        this.setArea(null);
    }
}
