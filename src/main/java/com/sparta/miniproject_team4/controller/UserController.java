package com.sparta.miniproject_team4.controller;

import com.sparta.miniproject_team4.dto.ResponseDto;
import com.sparta.miniproject_team4.dto.SignupRequestDto;
import com.sparta.miniproject_team4.model.Users;
import com.sparta.miniproject_team4.security.UserDetailsImpl;
import com.sparta.miniproject_team4.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/api/users/register")
    public ResponseDto signup(@RequestBody SignupRequestDto signupRequestDto){
        return userService.registerUser(signupRequestDto);
    }




//    // 이메일 중복 검사 - 모달
//    @PostMapping("/users/email")
//    public ResponseDto emailCheck(@RequestBody SignupRequestDto signupRequestDto){
//        return userService.emailCheck(signupRequestDto);
//    }

//    // 회원 로그인 페이지
//    @GetMapping("/users/login")
//    public String login() {
//        return "login";
//    }
//
//    // 회원 가입 페이지
//    @GetMapping("/users/register")
//    public String signup() {
//        return "signup";
//    }

    // 회원 가입 요청 처리
//    @PostMapping("/users/register")
//    public Users registerUser(SignupRequestDto requestDto) {
//        return userService.registerUser(requestDto);
//
//    }
// 유저 로그인 체크(삭제예정_HTML테스트용)
//    @GetMapping("/api/userCheck")
//    public String userCheck(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        if (userDetails != null) {
//            return userDetails.getUser().getUsername();
//        }
//        return "";
//    }



}