package org.electronic.store.springbootlogin.repository;

import org.electronic.store.springbootlogin.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifcationTokenRepository extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);
}
