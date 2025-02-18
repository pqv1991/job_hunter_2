package com.vietpq.JobHunter.respository;

import com.vietpq.JobHunter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
