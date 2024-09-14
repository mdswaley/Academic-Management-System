package com.example.collegedata.Service;

import com.example.collegedata.Dto.SubjectDto;
import com.example.collegedata.Entity.StudentEntity;
import com.example.collegedata.Entity.SubjectEntity;
import com.example.collegedata.Repository.StudentRepo;
import com.example.collegedata.Repository.SubjectRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectService {
    private final SubjectRepo subjectRepo;
    private final StudentRepo studentRepo;
    private final ModelMapper modelMapper;

    public SubjectService(SubjectRepo subjectRepo, StudentRepo studentRepo, ModelMapper modelMapper) {
        this.subjectRepo = subjectRepo;
        this.studentRepo = studentRepo;
        this.modelMapper = modelMapper;
    }

    public SubjectDto addSubject(SubjectDto subjectDto){
        SubjectEntity subjectEntity = modelMapper.map(subjectDto,SubjectEntity.class);
        return modelMapper.map(subjectRepo.save(subjectEntity),SubjectDto.class);
    }
    public SubjectDto getSubject(Long id){
        Optional<SubjectEntity> subjectEntity = subjectRepo.findById(id);
        return modelMapper.map(subjectEntity,SubjectDto.class);
    }

}
