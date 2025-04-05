package com.example.collegedata.Service;

import com.example.collegedata.Dto.AdmissionRecordDto;
import com.example.collegedata.Entity.AdmissionRecordEntity;
import com.example.collegedata.Entity.StudentEntity;
import com.example.collegedata.Repository.AdmissionRepo;
import com.example.collegedata.Repository.StudentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class AdmissionServiceTest {
    @InjectMocks
    private AdmissionService admissionService;

    @Mock
    private AdmissionRepo admissionRepo;

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private AdmissionRecordEntity admissionRecordEntity;


    @Mock
    private AdmissionRecordDto admissionRecordDto;

    @Spy
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        admissionRecordEntity = AdmissionRecordEntity.builder()
                .id(1L)
                .fees(8540)
                .paymentStatus("Paid")
                .paymentMethod("Cash")
                .studentDetails(new StudentEntity(1L,"MD", LocalDate.of(2003,6,9), 21, "Male", List.of(),List.of(), new AdmissionRecordEntity()))
                .build();

        admissionRecordDto = modelMapper.map(admissionRecordEntity, AdmissionRecordDto.class);
    }

//    Get Admission record

    @Test
    void getAdmissionRecord_whenNotPresent_thenThrowException(){
        when(admissionRepo.findById(99L)).thenReturn(Optional.empty());

        assertThat(admissionService.getRecord(99L)).isNull();
    }

    @Test
    void getAdmissionRecord_whenRecordPresent_thenReturnRecord(){
        when(admissionRepo.findById(1L)).thenReturn(Optional.of(admissionRecordEntity));

        assertThat(admissionService.getRecord(1L)).isEqualTo(admissionRecordDto);
    }


//    ADD Admission record

    @Test
    void addAdmissionRecord_whenAlreadyPresentByStudentName(){
        when(studentRepo.findByName(admissionRecordDto.getStudentDetails().getName())).thenReturn(Optional.of(admissionRecordDto.getStudentDetails()));

        assertThatThrownBy(()->admissionService.addRecord(admissionRecordDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Student with name MD is present.");

        verify(studentRepo, times(1)).findByName("MD");
        verify(admissionRepo,never()).save(admissionRecordEntity);
    }

    @Test
    void addAdmissionRecord_whenStudentIsNotPresent(){
        when(studentRepo.findByName(admissionRecordDto.getStudentDetails().getName())).thenReturn(Optional.empty());

        AdmissionRecordDto saveData = admissionService.addRecord(admissionRecordDto);

        assertThat(saveData).isNotNull();
        assertThat(saveData.getStudentDetails().getName()).isEqualTo("MD");

        verify(studentRepo, times(1)).findByName("MD");
        verify(admissionRepo, times(1)).save(admissionRecordEntity);
    }

//    Assign record to student
    @Test
    void updateAdmissionRecordToStudent_whenIdsAreNotPresent(){
        when(admissionRepo.findById(99L)).thenReturn(Optional.empty());
        when(studentRepo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(()->admissionService.assignAdmissionToStudent(99L,99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Admission Id is not present with 99");

        when(admissionRepo.findById(99L)).thenReturn(Optional.of(new AdmissionRecordEntity()));

        assertThatThrownBy(()->admissionService.assignAdmissionToStudent(99L,99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Student Id is not present with 99");

        verify(admissionRepo,never()).save(any(AdmissionRecordEntity.class));
    }


    @Test
    void updateAdmissionRecordToStudent_whenIdsArePresent(){
        when(admissionRepo.findById(1L)).thenReturn(Optional.of(admissionRecordEntity));
        when(studentRepo.findById(1L)).thenReturn(Optional.of(new StudentEntity()));
        when(admissionRepo.save(any(AdmissionRecordEntity.class))).thenAnswer(ans -> ans.getArgument(0));

        AdmissionRecordDto res = admissionService.assignAdmissionToStudent(1L,1L);

        assertThat(res).isNotNull();
        verify(admissionRepo).save(admissionRecordEntity);
    }









}