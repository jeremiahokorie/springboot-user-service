package com.legallync.userservice.repository;

import com.legallync.userservice.entity.User;
import com.legallync.userservice.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<UserModel> findByUserName(String userName);

    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}
