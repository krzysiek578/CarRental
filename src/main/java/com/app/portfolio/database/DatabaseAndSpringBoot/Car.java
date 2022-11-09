package com.app.portfolio.database.DatabaseAndSpringBoot;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

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
    private PetrolType  petrolType;
    private boolean enabled;

    public Car(String brand, String model, PetrolType petrolType, boolean enabled) {
        this.brand = brand;
        this.model = model;
        this.petrolType = petrolType;
        this.enabled = enabled;
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
}
