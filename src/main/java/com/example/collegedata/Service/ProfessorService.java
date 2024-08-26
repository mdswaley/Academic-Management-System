package com.example.collegedata.Service;

import com.example.collegedata.Entity.ProfessorEntity;
import com.example.collegedata.Entity.SubjectEntity;
import com.example.collegedata.Repository.ProfessorRepo;
import com.example.collegedata.Repository.SubjectRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfessorService {
    private final ProfessorRepo professorRepo;
    private final SubjectRepo subjectRepo;

    public ProfessorService(ProfessorRepo professorRepo, SubjectRepo subjectRepo) {
        this.professorRepo = professorRepo;
        this.subjectRepo = subjectRepo;
    }

    public ProfessorEntity addProfessor(ProfessorEntity professorEntity){
        return professorRepo.save(professorEntity);
    }
    public Optional<ProfessorEntity> getProfessor(Long id){
        return professorRepo.findById(id);
    }

    public ProfessorEntity assignSubjectToProfessor(Long proId, Long subId) {
        Optional<ProfessorEntity> professorEntity = professorRepo.findById(proId);
        Optional<SubjectEntity>  subjectEntity = subjectRepo.findById(subId);

        return professorEntity.flatMap(professorEntity1 ->
                subjectEntity.map(subjectEntity1 -> {
                    subjectEntity1.setProfessorId(professorEntity1);
                    subjectRepo.save(subjectEntity1);

                    professorEntity1.getSubjects().add(subjectEntity1); // get subject (null) the add new subject.
                    return professorEntity1;
                })).orElse(null);

    }
}
