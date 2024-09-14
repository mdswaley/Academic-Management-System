package com.example.collegedata.Controller;

import com.example.collegedata.Dto.StudentDto;
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
    public StudentDto addStudent(@RequestBody StudentDto studentDto){
        return studentService.addStudent(studentDto);
    }

    @GetMapping(path = "/{id}")
    public StudentDto getStudent(@PathVariable Long id){
        return studentService.getStudent(id);
    }

    @PutMapping(path = "/{stuId}/havingPro/{proId}")
    public StudentDto assignStudentToProfessor(@PathVariable Long stuId,
                                                      @PathVariable Long proId){
        return studentService.assignStudentToProfessor(stuId,proId);
    }

    @PutMapping(path = "/{stuId}/havingSub/{subId}")
    public StudentDto assignStudentToSubject(@PathVariable Long stuId,
                                                @PathVariable Long subId){
        return studentService.assignStudentToSubject(stuId,subId);
    }





}
