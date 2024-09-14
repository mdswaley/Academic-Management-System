package com.example.collegedata.Service;

import com.example.collegedata.Dto.StudentDto;
import com.example.collegedata.Entity.ProfessorEntity;
import com.example.collegedata.Entity.StudentEntity;
import com.example.collegedata.Entity.SubjectEntity;
import com.example.collegedata.Repository.ProfessorRepo;
import com.example.collegedata.Repository.StudentRepo;
import com.example.collegedata.Repository.SubjectRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepo studentRepo;
    private final ProfessorRepo professorRepo;
    private final SubjectRepo subjectRepo;
    private final ModelMapper modelMapper;



    public StudentService(StudentRepo studentRepo, ProfessorRepo professorRepo, SubjectRepo subjectRepo, ModelMapper modelMapper) {
        this.studentRepo = studentRepo;
        this.professorRepo = professorRepo;
        this.subjectRepo = subjectRepo;
        this.modelMapper = modelMapper;
    }

    public StudentDto addStudent(StudentDto studentDto){
        StudentEntity studentEntity = modelMapper.map(studentDto,StudentEntity.class);
        return modelMapper.map(studentRepo.save(studentEntity),StudentDto.class);
    }

    public StudentDto getStudent(Long id){
        Optional<StudentEntity> studentEntity = studentRepo.findById(id);
        return modelMapper.map(studentEntity,StudentDto.class);
    }

    public StudentDto assignStudentToProfessor(Long stuId, Long proId) {
        Optional<StudentEntity> studentEntity = studentRepo.findById(stuId);
        Optional<ProfessorEntity> professorEntity = professorRepo.findById(proId);

        StudentEntity saveStu = studentEntity.flatMap(studentEntity1 ->
                professorEntity.map(professorEntity1 -> {
                    professorEntity1.getListOfStudent().add(studentEntity1);
                    professorRepo.save(professorEntity1);

                    studentEntity1.getProfessors().add(professorEntity1);
                    return studentEntity1;
                })).orElse(null);

        if (saveStu != null){
            return modelMapper.map(saveStu,StudentDto.class);
        }
        return null;
    }

    public StudentDto assignStudentToSubject(Long stuId, Long subId) {
        Optional<SubjectEntity> subjectEntity = subjectRepo.findById(subId);
        Optional<StudentEntity> studentEntity = studentRepo.findById(stuId);

        StudentEntity saveStu = studentEntity.flatMap(studentEntity1 ->
                subjectEntity.map(subjectEntity1 -> {
                    subjectEntity1.getSubjectHavingStudents().add(studentEntity1);
                    subjectRepo.save(subjectEntity1);

                    studentEntity1.getAllSubjects().add(subjectEntity1);
                    studentRepo.save(studentEntity1);
                    return studentEntity1;
                })).orElse(null);

        if (saveStu != null){
            return modelMapper.map(saveStu,StudentDto.class);
        }
        return null;
    }
}
