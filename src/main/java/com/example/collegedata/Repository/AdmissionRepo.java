package com.example.collegedata.Repository;

import com.example.collegedata.Entity.AdmissionRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionRepo extends JpaRepository<AdmissionRecordEntity,Long> {
}
