package com.example.collegedata.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "Admissions")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer fees;

    @OneToOne
    @JoinColumn(name = "student_details_id")
    private StudentEntity studentDetails;
}
