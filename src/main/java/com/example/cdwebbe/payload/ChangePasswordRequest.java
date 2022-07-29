package com.example.cdwebbe.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
    String email;
    String password;
    String newPassword;
}
