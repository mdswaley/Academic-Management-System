package com.example.collegedata.Controller;

import com.example.collegedata.Dto.ProfessorDto;
import com.example.collegedata.Entity.ProfessorEntity;
import com.example.collegedata.Service.ProfessorService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/professor")
public class ProfessorController {
    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping
    public ProfessorDto addProfessor(@RequestBody ProfessorDto professorDto){
        return professorService.addProfessor(professorDto);
    }
    @GetMapping(path = "/{id}")
    public ProfessorDto getProfessor(@PathVariable Long id){
        return professorService.getProfessor(id);
    }

    @PutMapping(path = "{proId}/ofSubject/{subId}")
    public ProfessorDto assignSubjectToProfessor(@PathVariable Long proId,
                                                    @PathVariable Long subId){
        return professorService.assignSubjectToProfessor(proId,subId);
    }
}
