package com.vietpq.JobHunter.service.user;

import com.vietpq.JobHunter.dto.page.ResultPaginationDTO;
import com.vietpq.JobHunter.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.swing.text.html.HTMLDocument;
import java.util.List;
import java.util.Optional;

public interface UserService {
     User handleCreateUser(User user);
     void handleDeleteUser (long id);
     User fetchUserById(long id);
     ResultPaginationDTO fetchAllUsers(Specification<User> specification, Pageable pageable);
     User handleUpdateUser(User reqUser);
     User handleGetUserByUsername(String username);
}
