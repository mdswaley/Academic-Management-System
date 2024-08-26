package com.example.collegedata.Controller;

import com.example.collegedata.Entity.StudentEntity;
import com.example.collegedata.Entity.SubjectEntity;
import com.example.collegedata.Service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/subject")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    @PostMapping
    public SubjectEntity addSubject(@RequestBody SubjectEntity subject){
        return subjectService.addSubject(subject);
    }
    @GetMapping(path = "/{id}")
    public Optional<SubjectEntity> getSubject(@PathVariable Long id){
        return subjectService.getSubject(id);
    }


}
