package com.example.collegedata.Service;

import com.example.collegedata.Entity.AdmissionRecordEntity;
import com.example.collegedata.Entity.StudentEntity;
import com.example.collegedata.Repository.AdmissionRepo;
import com.example.collegedata.Repository.StudentRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdmissionService {
    private final AdmissionRepo admissionRepo;
    private final StudentRepo studentRepo;

    public AdmissionService(AdmissionRepo admissionRepo,StudentRepo studentRepo) {
        this.admissionRepo = admissionRepo;
        this.studentRepo = studentRepo;
    }

    public AdmissionRecordEntity addRecord(AdmissionRecordEntity admissionRecord){
        return admissionRepo.save(admissionRecord);
    }

    public Optional<AdmissionRecordEntity> getRecord(Long id){
        return admissionRepo.findById(id);
    }

    public AdmissionRecordEntity assignAdmissionToStudend(Long admId, Long stuId) {
        Optional<AdmissionRecordEntity> admissionRecordEntity = admissionRepo.findById(admId);
        Optional<StudentEntity> studentEntity = studentRepo.findById(stuId);


        return admissionRecordEntity.flatMap(admissionRecordEntity1 ->
            studentEntity.map(studentEntity1 -> {
                admissionRecordEntity1.setStudentDetails(studentEntity1);
                return admissionRepo.save(admissionRecordEntity1);
            })).orElse(null);
    }
}
