package com.sparta.miniproject_team4.controller;

import com.sparta.miniproject_team4.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {


    @GetMapping("/api/nickname")
    @ResponseBody
    public String currentNickName(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            return userDetails.getNickname();
        }else {
        return null;
        }
    }

    @GetMapping("/api")
    public boolean home(@AuthenticationPrincipal UserDetailsImpl userDetails){
        if (userDetails != null){
            return true;
        }
        return false;

    }


}