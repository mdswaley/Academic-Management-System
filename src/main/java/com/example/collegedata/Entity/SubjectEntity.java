package com.example.collegedata.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Subject")
@Audited
public class SubjectEntity extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String subjectName;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "professorId",referencedColumnName = "id")
//    @JsonIgnore
//    private ProfessorEntity professorId;

    @ManyToOne
    @JoinColumn(name = "professorId", referencedColumnName = "id")
    @JsonIgnore
    private ProfessorEntity professorId;

    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "AllSubjects")
    @JsonIgnore
    private List<StudentEntity> SubjectHavingStudents;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubjectEntity that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getSubjectName(), that.getSubjectName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSubjectName());
    }
}

