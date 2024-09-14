package com.example.collegedata.Dto;

import com.example.collegedata.Entity.ProfessorEntity;
import com.example.collegedata.Entity.StudentEntity;
import com.example.collegedata.Entity.SubjectEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectDto {
    private Long id;
    private String subjectName;
    private ProfessorEntity professorId;
    private List<StudentEntity> SubjectHavingStudents;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedData;

    private String createdBy;


    private String modifiedBy;


}
