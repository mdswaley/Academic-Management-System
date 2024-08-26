package com.example.collegedata.Controller;

import com.example.collegedata.Entity.AdmissionRecordEntity;
import com.example.collegedata.Entity.StudentEntity;
import com.example.collegedata.Service.AdmissionService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/Admission")
public class AdmissionController {
    private final AdmissionService admissionService;


    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @PostMapping
    public AdmissionRecordEntity addRecord(@RequestBody AdmissionRecordEntity admissionRecordEntity){
        return admissionService.addRecord(admissionRecordEntity);
    }

    @GetMapping(path = "/{id}")
    public Optional<AdmissionRecordEntity> getRecord(@PathVariable Long id){
        return admissionService.getRecord(id);
    }

    @PutMapping(path = "/{admId}/ofStudent/{stuId}")
    public AdmissionRecordEntity assignAdmissionToStudend(@PathVariable Long admId,
                                                          @PathVariable Long stuId){
        return admissionService.assignAdmissionToStudend(admId,stuId);
    }
}
