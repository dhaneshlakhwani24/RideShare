package com.dhaneshlakhwani.rideshare.mapper;

import com.dhaneshlakhwani.rideshare.entity.User;
import com.dhaneshlakhwani.rideshare.dto.user.UserDTO;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        return UserDTO.builder()
                .id(user.getId())
                .mobileNumber(user.getMobileNumber())
                .role(user.getRole())
                .active(user.getActive())
                .build();
    }
}
