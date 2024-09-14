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

@Entity
@Table(name = "Professor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class ProfessorEntity extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private Integer yearOfExperience;


    @OneToMany(mappedBy = "professorId",cascade = CascadeType.ALL)
    private List<SubjectEntity> subjects;

    @ManyToMany
    @JoinTable(name = "studentList",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "ProfessorId"))
    @JsonIgnore
    private List<StudentEntity> ListOfStudent;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfessorEntity that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getYearOfExperience(), that.getYearOfExperience());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getYearOfExperience());
    }
}
