package com.example.collegedata.Service;

import com.example.collegedata.Dto.AdmissionRecordDto;
import com.example.collegedata.Entity.AdmissionRecordEntity;
import com.example.collegedata.Entity.StudentEntity;
import com.example.collegedata.Repository.AdmissionRepo;
import com.example.collegedata.Repository.StudentRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
        AdmissionRecordEntity admissionRecordEntity = modelMapper.map(admissionRecord,AdmissionRecordEntity.class);
        admissionRepo.save(admissionRecordEntity);
//        return admissionRepo.save(admissionRecord);
        return modelMapper.map(admissionRecordEntity,AdmissionRecordDto.class);
    }

    public AdmissionRecordDto getRecord(Long id){
        Optional<AdmissionRecordEntity> admissionRecordEntity = admissionRepo.findById(id);
        return modelMapper.map(admissionRecordEntity,AdmissionRecordDto.class);

//        return admissionRepo.findById(id);
    }

    public AdmissionRecordDto assignAdmissionToStudend(Long admId, Long stuId) {
        Optional<AdmissionRecordEntity> admissionRecordEntity = admissionRepo.findById(admId);
        Optional<StudentEntity> studentEntity = studentRepo.findById(stuId);


        AdmissionRecordEntity updatedAdmission = admissionRecordEntity.flatMap(admissionRecordEntity1 ->
            studentEntity.map(studentEntity1 -> {
                admissionRecordEntity1.setStudentDetails(studentEntity1);
                return admissionRepo.save(admissionRecordEntity1);
            })).orElse(null);

        if(updatedAdmission != null){
            return modelMapper.map(updatedAdmission,AdmissionRecordDto.class);
        }

        return null;
    }
}
