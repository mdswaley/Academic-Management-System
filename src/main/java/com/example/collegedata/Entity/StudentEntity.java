package com.example.collegedata.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Student")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private Integer age;
    private String gender;

    @ManyToMany(mappedBy = "ListOfStudent")
    private List<ProfessorEntity> professors;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "AssignSubjectToStudent",joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
//    @JsonIgnore
    private List<SubjectEntity> AllSubjects;





    @OneToOne(mappedBy = "studentDetails")
    @JsonIgnore
    private AdmissionRecordEntity admissionRecordEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentEntity that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
