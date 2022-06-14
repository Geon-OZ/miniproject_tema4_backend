package com.sparta.miniproject_team4.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Setter
@Getter
public class SignupRequestDto {
    private String email;
    private String nickname;
    private String password;
}
