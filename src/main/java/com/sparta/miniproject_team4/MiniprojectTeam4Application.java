package com.sparta.miniproject_team4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MiniprojectTeam4Application {

    public static void main(String[] args) {
        SpringApplication.run(MiniprojectTeam4Application.class, args);
    }

}
