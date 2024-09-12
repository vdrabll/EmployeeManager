package com.example.EmployeeManager.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private String name;
    private String description;
}
