package com.bluty.springsecurity2023.repository;

import com.bluty.springsecurity2023.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query("SELECT p FROM UserEntity p WHERE UPPER(p.username) LIKE UPPER(:username)")
    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT p FROM UserEntity p WHERE UPPER(p.email) LIKE UPPER(:email)")
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByTokenRecover(String email);

}
