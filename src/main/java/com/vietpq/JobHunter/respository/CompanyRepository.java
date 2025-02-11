package com.vietpq.JobHunter.respository;

import com.vietpq.JobHunter.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    boolean existsByName(String name);
}
