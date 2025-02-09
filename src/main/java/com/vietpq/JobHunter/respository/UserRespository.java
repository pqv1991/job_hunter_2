package com.vietpq.JobHunter.respository;

import com.vietpq.JobHunter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRespository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
