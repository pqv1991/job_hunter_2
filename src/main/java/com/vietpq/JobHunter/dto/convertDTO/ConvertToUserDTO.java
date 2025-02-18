package com.vietpq.JobHunter.dto.convertDTO;

import com.vietpq.JobHunter.dto.user.ResCreateUserDTO;
import com.vietpq.JobHunter.dto.user.ResUpdateUserDTO;
import com.vietpq.JobHunter.dto.user.ResUserDTO;
import com.vietpq.JobHunter.entity.User;

public class ConvertToUserDTO {

    private static ResUserDTO buildBaseUserDTO(User user) {
        return new ResUserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getGender(),
                user.getAddress(),
                user.getCreateAt(),
                user.getUpdateAt()
        );
    }

    public static ResCreateUserDTO convertToResCreateUserDTO(User user) {
        ResUserDTO baseUserDTO = buildBaseUserDTO(user);
        return new ResCreateUserDTO(
                baseUserDTO.getId(),
                baseUserDTO.getName(),
                baseUserDTO.getEmail(),
                baseUserDTO.getAge(),
                baseUserDTO.getGender(),
                baseUserDTO.getAddress(),
                baseUserDTO.getCreateAt()
        );
    }

    public static ResUpdateUserDTO convertToResUpdateUserDTO(User user) {
        ResUserDTO baseUserDTO = buildBaseUserDTO(user);
        return new ResUpdateUserDTO(
                baseUserDTO.getId(),
                baseUserDTO.getName(),
                baseUserDTO.getEmail(),
                baseUserDTO.getAge(),
                baseUserDTO.getGender(),
                baseUserDTO.getAddress(),
                baseUserDTO.getUpdateAt()
        );
    }

    public static ResUserDTO convertToResUserDTO(User user) {
        return buildBaseUserDTO(user);
    }
}
