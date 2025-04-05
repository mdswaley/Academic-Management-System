package com.example.collegedata.Service;

import com.example.collegedata.Dto.AdmissionRecordDto;
import com.example.collegedata.Entity.AdmissionRecordEntity;
import com.example.collegedata.Entity.StudentEntity;
import com.example.collegedata.Repository.AdmissionRepo;
import com.example.collegedata.Repository.StudentRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AdmissionService {
    private final AdmissionRepo admissionRepo;
    private final StudentRepo studentRepo;
    private final ModelMapper modelMapper;

    public AdmissionService(AdmissionRepo admissionRepo, StudentRepo studentRepo, ModelMapper modelMapper) {
        this.admissionRepo = admissionRepo;
        this.studentRepo = studentRepo;
        this.modelMapper = modelMapper;
    }

    public AdmissionRecordDto addRecord(AdmissionRecordDto admissionRecord){
        Optional<StudentEntity> student = studentRepo.findByName(admissionRecord.getStudentDetails().getName());

        if(student.isPresent()){
            log.error("already student is present");
            throw new RuntimeException("Student with name "+student.get().getName()+" is present.");
        }
        AdmissionRecordEntity admissionRecordEntity = modelMapper.map(admissionRecord,AdmissionRecordEntity.class);
        admissionRepo.save(admissionRecordEntity);
        log.info("record added successfully.");
        return modelMapper.map(admissionRecordEntity,AdmissionRecordDto.class);
    }

    public AdmissionRecordDto getRecord(Long id){
        Optional<AdmissionRecordEntity> admissionRecordEntity = admissionRepo.findById(id);
        return modelMapper.map(admissionRecordEntity,AdmissionRecordDto.class);
    }

    public AdmissionRecordDto assignAdmissionToStudent(Long admId, Long stuId) {
        Optional<AdmissionRecordEntity> admissionRecordEntity = admissionRepo.findById(admId);
        Optional<StudentEntity> studentEntity = studentRepo.findById(stuId);

        if(admissionRecordEntity.isEmpty()){
            log.error("Admission Id is not present with {}", admId);
            throw new RuntimeException("Admission Id is not present with "+admId);
        }

        if(studentEntity.isEmpty()){
            log.error("Student Id is not present with {}", stuId);
            throw new RuntimeException("Student Id is not present with "+stuId);
        }


        AdmissionRecordEntity updatedAdmission = admissionRecordEntity.flatMap(admissionRecordEntity1 ->
            studentEntity.map(studentEntity1 -> {
                admissionRecordEntity1.setStudentDetails(studentEntity1);
                return admissionRepo.save(admissionRecordEntity1);
            })).orElse(null);

        return modelMapper.map(updatedAdmission,AdmissionRecordDto.class);
    }
}
