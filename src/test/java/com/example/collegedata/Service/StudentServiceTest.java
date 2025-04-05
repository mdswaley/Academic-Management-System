package com.example.collegedata.Service;

import com.example.collegedata.Dto.StudentDto;
import com.example.collegedata.Entity.AdmissionRecordEntity;
import com.example.collegedata.Entity.StudentEntity;
import com.example.collegedata.Repository.ProfessorRepo;
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
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentServiceTest {
    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private StudentEntity studentEntity;

    @Mock
    private StudentDto studentDto;

    @Mock
    private ProfessorRepo professorRepo;

    @Spy
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp(){
        studentEntity = StudentEntity.builder()
                .id(1L)
                .name("Swaley")
                .age(21)
                .dateOfBirth(LocalDate.of(2003,6,9))
                .gender("male")
                .AllSubjects(new ArrayList<>())
                .professors(new ArrayList<>())
                .admissionRecordEntity(new AdmissionRecordEntity())
                .build();

        studentDto = modelMapper.map(studentEntity,StudentDto.class);
    }

    @Test
    void addStudent_ifAlreadyPresent_throwException(){
        when(studentRepo.findByName(studentDto.getName())).thenReturn(Optional.of(studentEntity));

        assertThatThrownBy(()->studentService.addStudent(studentDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Student with name is not present : Swaley");

        verify(studentRepo,times(1)).findByName("Swaley");
        verify(studentRepo,never()).save(any(StudentEntity.class));
    }


    @Test
    void addStudent_ifStudentNotPresent_thenAdd(){
        when(studentRepo.findByName(studentDto.getName())).thenReturn(Optional.empty());

        StudentDto save = studentService.addStudent(studentDto);

        assertThat(save).isNotNull();
        assertThat(save.getName()).isEqualTo(studentDto.getName());

        verify(studentRepo,times(1)).findByName("Swaley");
        verify(studentRepo,times(1)).save(any(StudentEntity.class));
    }




}