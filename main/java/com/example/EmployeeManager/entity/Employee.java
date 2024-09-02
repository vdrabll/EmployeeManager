package com.example.EmployeeManager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "boolean default true")
    private Boolean isWorkingNow;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Schedule> schedule;

    @ManyToMany
    @JoinTable(name = "employees_departments",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    private List<Department> department;

    @OneToMany(mappedBy = "employee")
    private List<PositionHistory> positionHistoryList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "employee")
    private List<SalaryHistory> salaryHistories;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ManyToMany
    @JoinTable(name = "employees_projects",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects;

}
