package com.dhaneshlakhwani.rideshare.dto.user;

import com.dhaneshlakhwani.rideshare.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String mobileNumber;
    private Role role;
    private Boolean active;
}