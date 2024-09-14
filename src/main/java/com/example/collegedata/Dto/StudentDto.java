package com.example.collegedata.Dto;

import com.example.collegedata.Entity.ProfessorEntity;
import com.example.collegedata.Entity.SubjectEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentDto {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private Integer age;
    private String gender;
    private List<ProfessorEntity> professors;
    private List<SubjectEntity> AllSubjects;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedData;

    private String createdBy;


    private String modifiedBy;
}
