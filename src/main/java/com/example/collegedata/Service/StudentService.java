package com.example.collegedata.Service;

import com.example.collegedata.Entity.ProfessorEntity;
import com.example.collegedata.Entity.StudentEntity;
import com.example.collegedata.Entity.SubjectEntity;
import com.example.collegedata.Repository.ProfessorRepo;
import com.example.collegedata.Repository.StudentRepo;
import com.example.collegedata.Repository.SubjectRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepo studentRepo;
    private final ProfessorRepo professorRepo;

    private final SubjectRepo subjectRepo;



    public StudentService(StudentRepo studentRepo, ProfessorRepo professorRepo, SubjectRepo subjectRepo) {
        this.studentRepo = studentRepo;
        this.professorRepo = professorRepo;
        this.subjectRepo = subjectRepo;
    }

    public StudentEntity addStudent(StudentEntity studentEntity){
        return studentRepo.save(studentEntity);
    }

    public Optional<StudentEntity> getStudent(Long id){
        Optional<StudentEntity> studentEntity = studentRepo.findById(id);
        return studentEntity;
    }

    public StudentEntity assignStudentToProfessor(Long stuId, Long proId) {
        Optional<StudentEntity> studentEntity = studentRepo.findById(stuId);
        Optional<ProfessorEntity> professorEntity = professorRepo.findById(proId);

        return studentEntity.flatMap(studentEntity1 ->
                professorEntity.map(professorEntity1 -> {
                    professorEntity1.getListOfStudent().add(studentEntity1);
                    professorRepo.save(professorEntity1);

                    studentEntity1.getProfessors().add(professorEntity1);
                    return studentEntity1;
                })).orElse(null);
    }

    public StudentEntity assignStudentToSubject(Long stuId, Long subId) {
        Optional<SubjectEntity> subjectEntity = subjectRepo.findById(subId);
        Optional<StudentEntity> studentEntity = studentRepo.findById(stuId);

        return studentEntity.flatMap(studentEntity1 ->
                subjectEntity.map(subjectEntity1 -> {
                    subjectEntity1.getSubjectHavingStudents().add(studentEntity1);
                    subjectRepo.save(subjectEntity1);

                    studentEntity1.getAllSubjects().add(subjectEntity1);
                    studentRepo.save(studentEntity1);
                    return studentEntity1;
                })).orElse(null);
    }
}
