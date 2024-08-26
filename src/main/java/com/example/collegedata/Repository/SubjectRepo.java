package com.example.collegedata.Repository;

import com.example.collegedata.Entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepo extends JpaRepository<SubjectEntity,Long> {
}
