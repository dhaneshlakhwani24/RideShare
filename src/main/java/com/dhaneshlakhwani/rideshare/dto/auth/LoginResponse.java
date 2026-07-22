package com.dhaneshlakhwani.rideshare.dto.auth;

import com.dhaneshlakhwani.rideshare.enums.Role;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long userId;
    private Role role;
    private String redirectUrl;
    private String fullName;
}
