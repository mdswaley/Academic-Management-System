package com.example.collegedata.Service;

import com.example.collegedata.Dto.ProfessorDto;
import com.example.collegedata.Entity.ProfessorEntity;
import com.example.collegedata.Entity.SubjectEntity;
import com.example.collegedata.Repository.ProfessorRepo;
import com.example.collegedata.Repository.SubjectRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ProfessorService {
    private final ProfessorRepo professorRepo;
    private final SubjectRepo subjectRepo;
    private final ModelMapper modelMapper;

    public ProfessorService(ProfessorRepo professorRepo, SubjectRepo subjectRepo, ModelMapper modelMapper) {
        this.professorRepo = professorRepo;
        this.subjectRepo = subjectRepo;
        this.modelMapper = modelMapper;
    }

    public ProfessorDto addProfessor(ProfessorDto professorDto){
        Optional<ProfessorEntity> professorEntity = professorRepo.findByName(professorDto.getName());

        if(professorEntity.isPresent()){
            log.error("Professor with name {}{}",professorDto.getName()," is Present.");
            throw new RuntimeException("Professor is present with name : "+professorDto.getName());
        }

        ProfessorEntity professorEntity1 = modelMapper.map(professorDto,ProfessorEntity.class);
        professorRepo.save(professorEntity1);
        log.info("save professor with name : {}",professorDto.getName());
        return modelMapper.map(professorEntity1,ProfessorDto.class);
    }

    public ProfessorDto getProfessor(Long id){
        Optional<ProfessorEntity> professorEntity = professorRepo.findById(id);
        if(professorEntity.isEmpty()){
            log.error("Professor with id {}{}",id," not present.");
            throw new RuntimeException("Professor is not present with id "+id);
        }
        log.info("Successfully get Professor.");
        return modelMapper.map(professorEntity,ProfessorDto.class);
    }

    public ProfessorDto assignSubjectToProfessor(Long proId, Long subId) {
        Optional<ProfessorEntity> professorEntity = professorRepo.findById(proId);
        Optional<SubjectEntity>  subjectEntity = subjectRepo.findById(subId);

        if(professorEntity.isEmpty()){
            log.error("professor is not present with id {}",proId);
            throw new RuntimeException("Professor is not present");
        }

        if(subjectEntity.isEmpty()){
            log.error("subject is not present with id {}",subId);
            throw new RuntimeException("subject is not present");
        }

        ProfessorEntity savePrf = professorEntity.flatMap(professorEntity1 ->
                subjectEntity.map(subjectEntity1 -> {
                    subjectEntity1.setProfessorId(professorEntity1);
                    subjectRepo.save(subjectEntity1);

                    professorEntity1.getSubjects().add(subjectEntity1); // get subject (null) the add new subject.
                    return professorEntity1;
                })).orElse(null);

        log.info("Successfully assign professor to subject.");
        return modelMapper.map(savePrf,ProfessorDto.class);

    }
}
