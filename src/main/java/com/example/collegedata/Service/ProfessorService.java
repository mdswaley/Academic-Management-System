package com.example.collegedata.Service;

import com.example.collegedata.Dto.ProfessorDto;
import com.example.collegedata.Entity.ProfessorEntity;
import com.example.collegedata.Entity.SubjectEntity;
import com.example.collegedata.Repository.ProfessorRepo;
import com.example.collegedata.Repository.SubjectRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
        ProfessorEntity professorEntity = modelMapper.map(professorDto,ProfessorEntity.class);
        professorRepo.save(professorEntity);
        return modelMapper.map(professorEntity,ProfessorDto.class);
    }

    public ProfessorDto getProfessor(Long id){
        Optional<ProfessorEntity> professorEntity = professorRepo.findById(id);
        return modelMapper.map(professorEntity,ProfessorDto.class);
    }

    public ProfessorDto assignSubjectToProfessor(Long proId, Long subId) {
        Optional<ProfessorEntity> professorEntity = professorRepo.findById(proId);
        Optional<SubjectEntity>  subjectEntity = subjectRepo.findById(subId);

        ProfessorEntity savePrf = professorEntity.flatMap(professorEntity1 ->
                subjectEntity.map(subjectEntity1 -> {
                    subjectEntity1.setProfessorId(professorEntity1);
                    subjectRepo.save(subjectEntity1);

                    professorEntity1.getSubjects().add(subjectEntity1); // get subject (null) the add new subject.
                    return professorEntity1;
                })).orElse(null);

        if (savePrf != null){
            return modelMapper.map(savePrf,ProfessorDto.class);
        }

        return null;

    }
}
