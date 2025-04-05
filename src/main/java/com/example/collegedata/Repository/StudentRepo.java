package com.example.collegedata.Repository;

import com.example.collegedata.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<StudentEntity,Long> {
    Optional<StudentEntity> findByName(String name);
}
