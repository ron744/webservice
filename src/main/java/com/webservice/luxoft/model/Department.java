package com.webservice.luxoft.model;

import lombok.*;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
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

//    @Type(type = "com.webservice.luxoft.model.Department")
//    @Columns(columns = {@Column(name = "department_id"), @Column(name = "name"), @Column(name = "description"), @Column(name = "parent"), @Column(name = "employees")})

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_department_id")
    private Department mainDepartment;

//    @OneToMany(mappedBy = "department")
//    private List<Department> departments;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private Collection<Employee> employees;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department dep = (Department) o;
        return dep.getName().equals(this.name) && dep.getDescription().equals(this.description)/* && dep.parent.equals(this.parent) */&& dep.employees.containsAll(this.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, /*parent,*/ employees);
    }
}
