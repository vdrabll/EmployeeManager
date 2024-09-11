package com.example.EmployeeManager.entity;

import com.example.EmployeeManager.enums.AuthRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @Enumerated(EnumType.STRING)
    private AuthRole id;
}
