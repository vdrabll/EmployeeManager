package com.example.EmployeeManager.entity;

import com.example.EmployeeManager.enums.LeaveStatus;
import com.example.EmployeeManager.enums.LeaveType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date startDate;
    @Column(nullable = false)
    private Date endDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveStatus status;

}
