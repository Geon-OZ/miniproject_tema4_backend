package com.sparta.miniproject_team4.repository;

import com.sparta.miniproject_team4.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByNickname(String nickname);
    Optional<Users> findByEmail(String email);

}