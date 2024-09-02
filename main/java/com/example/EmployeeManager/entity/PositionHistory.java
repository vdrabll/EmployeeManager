package com.example.EmployeeManager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(nullable = false)
    private Date startDate;

    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
}
