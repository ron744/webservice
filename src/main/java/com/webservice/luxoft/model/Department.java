package com.webservice.luxoft.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "department_id")
    private Long id;
    private String name;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_department_id")
    private Department mainDepartment;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Employee> employees;

    public Department(String name, String description, Department mainDepartment, Collection<Employee> employees) {
        this.name = name;
        this.description = description;
        this.mainDepartment = mainDepartment;
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department dep = (Department) o;
        return dep.getName().equals(this.name) && dep.getDescription().equals(this.description) && dep.mainDepartment.equals(this.mainDepartment) && dep.employees.containsAll(this.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, mainDepartment, employees);
    }
}
