package com.example.collegedata.Repository;

import com.example.collegedata.Entity.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorRepo extends JpaRepository<ProfessorEntity,Long> {
    Optional<ProfessorEntity> findByName(String name);
}
