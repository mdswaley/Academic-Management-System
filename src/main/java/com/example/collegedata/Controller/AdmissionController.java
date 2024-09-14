package com.example.collegedata.Controller;

import com.example.collegedata.Dto.AdmissionRecordDto;
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
    public AdmissionRecordDto addRecord(@RequestBody AdmissionRecordDto admissionRecordDto){
        return admissionService.addRecord(admissionRecordDto);
    }

    @GetMapping(path = "/{id}")
    public AdmissionRecordDto getRecord(@PathVariable Long id){
        return admissionService.getRecord(id);
    }

    @PutMapping(path = "/{admId}/ofStudent/{stuId}")
    public AdmissionRecordDto assignAdmissionToStudent(@PathVariable Long admId,
                                                          @PathVariable Long stuId){
        return admissionService.assignAdmissionToStudend(admId,stuId);
    }
}
