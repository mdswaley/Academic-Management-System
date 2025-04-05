package com.example.collegedata.Service;

import com.example.collegedata.Dto.ProfessorDto;
import com.example.collegedata.Entity.ProfessorEntity;
import com.example.collegedata.Entity.SubjectEntity;
import com.example.collegedata.Repository.ProfessorRepo;
import com.example.collegedata.Repository.SubjectRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProfessorServiceTest {
    @InjectMocks
    private ProfessorService professorService;

    @Mock
    private ProfessorDto professorDto;

    @Mock
    private ProfessorEntity professorEntity;

    @Mock
    private SubjectEntity subjectEntity;

    @Mock
    private SubjectRepo subjectRepo;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private ProfessorRepo professorRepo;

    @BeforeEach
    void setUp(){
        professorEntity = ProfessorEntity.builder()
                .id(1L)
                .name("Ankita")
                .ListOfStudent(List.of())
                .subjects(new ArrayList<>())
                .yearOfExperience(3)
                .build();

        professorDto = modelMapper.map(professorEntity, ProfessorDto.class);
    }


//    Add professor
    @Test
    void addProfessor_whenAlreadyExist_ThenThrowException(){
        when(professorRepo.findByName(professorDto.getName())).thenReturn(Optional.of(professorEntity));

        assertThatThrownBy(()->professorService.addProfessor(professorDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Professor is present with name : Ankita");

        verify(professorRepo,never()).save(any(ProfessorEntity.class));
    }

    @Test
    void addProfessor_whenNotExist_ThenAddProfessor(){
        when(professorRepo.findByName(professorDto.getName())).thenReturn(Optional.empty());

        ProfessorDto saveProf = professorService.addProfessor(professorDto);

        assertThat(saveProf).isNotNull();
        assertThat(saveProf.getName()).isEqualTo(professorDto.getName());

        verify(professorRepo,times(1)).findByName("Ankita");
        verify(professorRepo,times(1)).save(any(ProfessorEntity.class));
    }

//    Get Professor
    @Test
    void getProfessor_whenNotPresent_thenThrowException(){
        when(professorRepo.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(()->professorService.getProfessor(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Professor is not present with id 1");

        verify(professorRepo, times(1)).findById(1L);
    }

    @Test
    void getProfessor_whenIsPresent_thenReturnProfessor(){
        when(professorRepo.findById(1L)).thenReturn(Optional.of(professorEntity));

        assertThat(professorService).isNotNull();
        assertThat(professorService.getProfessor(1L)).isEqualTo(professorDto);
//        assertThat(professorEntity.getYearOfExperience()).isEqualTo(professorDto.getYearOfExperience());

        verify(professorRepo, times(1)).findById(1L);
    }

//    Assign subject to professor
    @Test
    void assignSubjectToProfessor_isProfNotPresent_throwException(){
        when(professorRepo.findById(99L)).thenReturn(Optional.empty());
        when(subjectRepo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(()->professorService.assignSubjectToProfessor(99L,99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Professor is not present");

        when(professorRepo.findById(99L)).thenReturn(Optional.of(new ProfessorEntity()));

        assertThatThrownBy(()->professorService.assignSubjectToProfessor(99L,99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("subject is not present");

        verify(subjectRepo,never()).save(any(SubjectEntity.class));
    }

    @Test
    void assignSubjectToProfessor_ifValidId_ThenSave(){
        when(professorRepo.findById(1L)).thenReturn(Optional.of(professorEntity));
        when(subjectRepo.findById(1L)).thenReturn(Optional.of(subjectEntity));

        ProfessorDto save = professorService.assignSubjectToProfessor(1L,1L);
        assertThat(save).isNotNull();

        verify(subjectRepo, times(1)).save(any(SubjectEntity.class));
        verify(professorRepo, times(0)).save(any(ProfessorEntity.class));
    }



}