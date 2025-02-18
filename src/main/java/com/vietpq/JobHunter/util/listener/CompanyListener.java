package com.vietpq.JobHunter.util.listener;

import com.vietpq.JobHunter.entity.Company;
import com.vietpq.JobHunter.util.security.SecurityUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class CompanyListener {
    @PrePersist
    public void beforeAnyCreate(Company company){
        company.setCreateAt(Instant.now());
        company.setCreateBy(SecurityUtil.getCurrentUserLogin().isPresent() == true ? SecurityUtil.getCurrentUserLogin().get() : "");
    }
    @PreUpdate
    public void beforeAnyUpadate(Company company){
        company.setUpdateAt(Instant.now());
        company.setUpdateBy(SecurityUtil.getCurrentUserLogin().isPresent() == true ? SecurityUtil.getCurrentUserLogin().get() : "");
    }
}
