package com.vietpq.JobHunter.service.user;

import com.vietpq.JobHunter.dto.page.ResultPaginationDTO;
import com.vietpq.JobHunter.dto.user.ResUserDTO;
import com.vietpq.JobHunter.entity.User;
import com.vietpq.JobHunter.exception.DuplicatedException;
import com.vietpq.JobHunter.exception.NotFoundException;
import com.vietpq.JobHunter.exception.message.UserMessage;
import com.vietpq.JobHunter.respository.UserRepository;
import com.vietpq.JobHunter.util.validator.AuthValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User handleCreateUser(User user) {
        AuthValidator.validateUserUpdate(user);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicatedException(UserMessage.EMAIL_ALREADY_EXIST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void handleDeleteUser(long id) {
        User user = fetchUserById(id);
        userRepository.delete(user);
    }

    @Override
    public User fetchUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(UserMessage.NOT_FOUND));
    }

    @Override
    public ResultPaginationDTO fetchAllUsers(Specification<User> specification, Pageable pageable) {
        Page<User> userPage = userRepository.findAll(specification, pageable);

        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(userPage.getTotalPages());
        meta.setTotal(userPage.getTotalElements());

        List<ResUserDTO> listUser = userPage.getContent().stream()
                .map(user -> new ResUserDTO(
                        user.getId(), user.getName(), user.getEmail(), user.getAge(),
                         user.getGender(),user.getAddress(), user.getCreateAt(), user.getUpdateAt()))
                .collect(Collectors.toList());

        return new ResultPaginationDTO(meta, listUser);
    }

    @Override
    public User handleUpdateUser(User user) {
        User userUpdate = fetchUserById(user.getId());
        userUpdate.setName(user.getName());
        userUpdate.setAddress(user.getAddress());
        userUpdate.setAge(user.getAge());
        userUpdate.setGender(user.getGender());
        userUpdate.setPhoneNumber(user.getPhoneNumber());
        return userRepository.save(userUpdate);
    }

    @Override
    public User handleGetUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByEmail(username))
                .orElseThrow(() -> new NotFoundException(UserMessage.NOT_FOUND));
    }
}
