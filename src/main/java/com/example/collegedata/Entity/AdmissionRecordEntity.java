package com.example.collegedata.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "Admissions")
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class AdmissionRecordEntity extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer fees;

    private String paymentStatus; //paid, pending or partially paid

    private String paymentMethod; //case or online

    @OneToOne
    @JoinColumn(name = "student_details_id")
    private StudentEntity studentDetails;
}
