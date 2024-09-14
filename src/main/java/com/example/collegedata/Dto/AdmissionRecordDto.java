package com.example.collegedata.Dto;

import com.example.collegedata.Entity.StudentEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdmissionRecordDto {
    private Long id;

    private Integer fees;

    private String paymentStatus; //paid, pending or partially paid

    private String paymentMethod; //cash or online

    private StudentEntity studentDetails;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedData;

    private String createdBy;


    private String modifiedBy;
}
