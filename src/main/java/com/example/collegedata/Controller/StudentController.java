package com.example.collegedata.Controller;

import com.example.collegedata.Entity.StudentEntity;
import com.example.collegedata.Entity.SubjectEntity;
import com.example.collegedata.Service.StudentService;
import com.example.collegedata.Service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public StudentEntity addStudent(@RequestBody StudentEntity studentEntity){
        return studentService.addStudent(studentEntity);
    }

    @GetMapping(path = "/{id}")
    public Optional<StudentEntity> getStudent(@PathVariable Long id){
        return studentService.getStudent(id);
    }

    @PutMapping(path = "/{stuId}/havingPro/{proId}")
    public StudentEntity assignStudentToProfessor(@PathVariable Long stuId,
                                                      @PathVariable Long proId){
        return studentService.assignStudentToProfessor(stuId,proId);
    }

    @PutMapping(path = "/{stuId}/havingSub/{subId}")
    public StudentEntity assignStudentToSubject(@PathVariable Long stuId,
                                                @PathVariable Long subId){
        return studentService.assignStudentToSubject(stuId,subId);
    }





}
