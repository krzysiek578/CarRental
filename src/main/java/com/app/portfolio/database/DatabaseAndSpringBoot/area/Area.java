package com.app.portfolio.database.DatabaseAndSpringBoot.area;


import com.app.portfolio.database.DatabaseAndSpringBoot.deprtment.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "areas")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "area")
    private Set<Department> departments = new HashSet<>();

    public Area(String name) {
        this.name = name;
    }

    public void addDepartment(Department department) {
        this.departments.add(department);
        department.setArea(this);
    }

    public void removeDepartment(Department department) {
        this.departments.remove(department);
        department.setArea(null);
    }
}
