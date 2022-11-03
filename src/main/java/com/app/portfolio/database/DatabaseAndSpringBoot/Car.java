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
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String brand;
    private String model;
    @Enumerated(EnumType.STRING)
    private PetrolTyp  petroType;
    private boolean enabled;

    public Car(String brand, String model, PetrolTyp petroType, boolean enabled) {
        this.brand = brand;
        this.model = model;
        this.petroType = petroType;
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return enabled == car.enabled && Objects.equals(id, car.id) && Objects.equals(brand, car.brand) && Objects.equals(model, car.model) && petroType == car.petroType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, petroType, enabled);
    }
}
