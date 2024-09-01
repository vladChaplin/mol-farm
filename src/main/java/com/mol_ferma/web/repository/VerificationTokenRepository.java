package com.mol_ferma.web.repository;

import com.mol_ferma.web.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findVerificationTokenBy(String verificationToken);
}
