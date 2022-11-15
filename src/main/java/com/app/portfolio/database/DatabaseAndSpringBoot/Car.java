package com.app.portfolio.database.DatabaseAndSpringBoot;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cars")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// GenerationType
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String brand;
    private String model;

    @Enumerated(EnumType.STRING)
    private PetrolType petrolType;
    private boolean enabled;

    @ManyToMany(mappedBy = "carSet")
    private Set<Department> departmentsSet = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private RentalOffice rentalOffice;

    public Car(String brand, String model, PetrolType petrolType, boolean enabled) {
        this.brand = brand;
        this.model = model;
        this.petrolType = petrolType;
        this.enabled = enabled;
    }

    public void setRentalOffice(RentalOffice rentalOffice) {
        this.rentalOffice = rentalOffice;
    }

    public void removeRentalOffice() {
        rentalOffice = null;
    }

    public void addDepartment(Department department) {
        this.departmentsSet.add(department);
        department.getCarSet().add(this);
    }

    public void removeDepartment(Department department) {
        this.departmentsSet.remove(department);
        department.getCarSet().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return enabled == car.enabled && Objects.equals(id, car.id) && Objects.equals(brand, car.brand) && Objects.equals(model, car.model) && petrolType == car.petrolType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, petrolType, enabled);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", petrolType=" + petrolType +
                ", enabled=" + enabled +
                '}';
    }
}
