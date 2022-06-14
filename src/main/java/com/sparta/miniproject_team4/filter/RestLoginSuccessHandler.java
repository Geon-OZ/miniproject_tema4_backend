package com.sparta.miniproject_team4.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.miniproject_team4.dto.ResponseDto;
import com.sparta.miniproject_team4.model.Users;
import com.sparta.miniproject_team4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class RestLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        ResponseDto responseDto = new ResponseDto();

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails)principal).getUsername();
            Users users = userRepository.findByEmail(email).orElse(null);

            if(users == null){
                responseDto.setResult(false);
                responseDto.setErr_msg("없는 회원입니다");
            }else{
                responseDto.setResult(true);
                responseDto.setEmail(users.getEmail());
            }
        }


        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(out, responseDto);
        out.flush();


    }
}
