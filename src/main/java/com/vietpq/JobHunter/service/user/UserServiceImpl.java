package com.vietpq.JobHunter.service.user;

import com.vietpq.JobHunter.entity.User;
import com.vietpq.JobHunter.respository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public  class UserServiceImpl implements UserService{

    private final UserRepository userRespository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRespository, PasswordEncoder passwordEncoder) {
        this.userRespository = userRespository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User handleCreateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return   this.userRespository.save(user);
    }

    @Override
    public void handleDeleteUser(long id) {
        this.userRespository.deleteById(id);
    }

    @Override
    public User getUserById(long id) {
        Optional<User> userOptional = this.userRespository.findById(id);
        if(userOptional.isPresent()){
            return userOptional.get();
        }
        return null;
    }

    @Override
    public List<User> getAlluser() {
        return this.userRespository.findAll();
    }

    @Override
    public User handleUpdateUser(User reqUser) {
        User curentUser = this.getUserById(reqUser.getId());
        if(curentUser != null){
            curentUser.setEmail(reqUser.getEmail());
             curentUser.setName(reqUser.getName());
             curentUser.setPassword(reqUser.getPassword());
             curentUser = this.userRespository.save(curentUser);
        }
        return curentUser;
    }

    @Override
    public User handleGetUserByUsername(String username) {
        return this.userRespository.findByEmail(username);
    }


}
