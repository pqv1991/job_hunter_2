package com.vietpq.JobHunter.util.listener;

import com.vietpq.JobHunter.entity.Company;
import com.vietpq.JobHunter.entity.User;
import com.vietpq.JobHunter.util.security.SecurityUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class UserListener {
    @PrePersist
    public void beforeAnyCreate(User user){
        user.setCreateAt(Instant.now());
        user.setCreateBy(SecurityUtil.getCurrentUserLogin().isPresent() == true ? SecurityUtil.getCurrentUserLogin().get() : "");
    }
    @PreUpdate
    public void beforeAnyUpadate(User user){
        user.setUpdateAt(Instant.now());
        user.setUpdateBy(SecurityUtil.getCurrentUserLogin().isPresent() == true ? SecurityUtil.getCurrentUserLogin().get() : "");
    }
}
