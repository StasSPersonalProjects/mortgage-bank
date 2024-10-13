package com.mortgageBank.authService.repositories;

import com.mortgageBank.authService.model.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokensRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    @Query(value =
            "SELECT * FROM tokens WHERE user_id = :id AND expired = FALSE AND revoked = FALSE",
            nativeQuery = true)
    Optional<List<Token>> findAllValidTokensByUser(@Param("id") long id);
}
