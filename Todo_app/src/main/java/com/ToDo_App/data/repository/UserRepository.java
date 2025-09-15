package com.ToDo_App.data.repository;

import com.ToDo_App.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.userName = :username")
    Optional<User> findByUsername(@Param("userName") String userName);

    @Query("SELECT u FROM User u WHERE u.userName = :username OR u.email = :email")
    Optional<User> findByUsernameOrEmail(@Param("userName") String userName, @Param("email") String email);

    String userName(String userName);
}
