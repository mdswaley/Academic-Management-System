package com.example.collegedata.Service;

import com.example.collegedata.Entity.StudentEntity;
import com.example.collegedata.Entity.SubjectEntity;
import com.example.collegedata.Repository.StudentRepo;
import com.example.collegedata.Repository.SubjectRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectService {
    private final SubjectRepo subjectRepo;
    private final StudentRepo studentRepo;

    public SubjectService(SubjectRepo subjectRepo, StudentRepo studentRepo) {
        this.subjectRepo = subjectRepo;
        this.studentRepo = studentRepo;
    }

    public SubjectEntity addSubject(SubjectEntity subjectEntity){
        return subjectRepo.save(subjectEntity);
    }
    public Optional<SubjectEntity> getSubject(Long id){
        return subjectRepo.findById(id);
    }

}
