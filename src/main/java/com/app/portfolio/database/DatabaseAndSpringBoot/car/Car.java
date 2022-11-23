package com.app.portfolio.database.DatabaseAndSpringBoot.car;



import com.app.portfolio.database.DatabaseAndSpringBoot.Model;
import com.app.portfolio.database.DatabaseAndSpringBoot.department.Department;
import com.app.portfolio.database.DatabaseAndSpringBoot.PetrolType;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.RentalOffice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

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
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "cars")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// GenerationType
public class Car implements Model<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;

    @Enumerated(EnumType.STRING)
    private PetrolType petrolType;
    private boolean enabled;

    @ManyToMany(mappedBy = "cars")
    private Set<Department> departments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private RentalOffice rentalOffice;




    public Car(String brand, String model, PetrolType petrolType, boolean enabled) {
        super();
        this.brand = brand;
        this.model = model;
        this.petrolType = petrolType;
        this.enabled = enabled;
    }


    public void addDepartment(Department department) {
        this.getDepartments().add(department);
        department.getCars().add(this);
    }

    public void removeDepartment(Department department) {
        this.getDepartments().remove(department);
        department.getCars().remove(this);
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
