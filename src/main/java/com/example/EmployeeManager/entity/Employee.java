package com.example.EmployeeManager.entity;

import com.example.EmployeeManager.enums.AuthRole;
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

    @Column(nullable = false, length = 250)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Schedule> schedule;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "employees_departments", joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    private List<Department> department;

    @OneToMany(mappedBy = "employee")
    private List<PositionHistory> positionHistoryList;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private AuthRole role;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<SalaryHistory> salaryHistories;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "employees_projects", joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects;

}
