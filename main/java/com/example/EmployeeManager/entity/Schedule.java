package com.example.EmployeeManager.entity;

import com.example.EmployeeManager.enums.LocationType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.sql.Date;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Time startTime;

    @Column(nullable = false)
    private Time endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LocationType location;

}
