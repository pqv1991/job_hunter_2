package com.vietpq.JobHunter.service.user;

import com.vietpq.JobHunter.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
     User handleCreateUser(User user);
     void handleDeleteUser (long id);
     User getUserById(long id);
     List<User> getAlluser();
     User handleUpdateUser(User reqUser);
}
