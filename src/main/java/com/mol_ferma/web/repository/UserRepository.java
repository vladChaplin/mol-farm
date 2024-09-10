package com.mol_ferma.web.repository;

import com.mol_ferma.web.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    Optional<UserEntity> findByEmailOptional(String email);
    UserEntity findByPhoneNumber(String phoneNumber);
    UserEntity findByUsername(String username);
    UserEntity findFirstByEmail(String email);
}
