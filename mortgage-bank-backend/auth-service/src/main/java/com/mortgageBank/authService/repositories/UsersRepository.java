package com.mortgageBank.authService.repositories;

import com.mortgageBank.authService.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {

    @Query("SELECT COUNT(u) FROM User u WHERE u.isAccountNonLocked = TRUE AND u.isEnabled = TRUE")
    int countActiveUsers();

    Optional<User> findByUsername(String username);

    Optional<User> findByFullName(String fullName);

    Optional<User> findByIdCardNumber(String idCardNumber);

    @Modifying
    @Query(value = "UPDATE users SET last_login = :lastLogin WHERE id = :id", nativeQuery = true)
    @Transactional
    void updateLastLoginDateTime(@Param("lastLogin") LocalDateTime lastLogin, @Param("id") long id);

    @Query(value =
            "SELECT * FROM users " +
                    "WHERE username = :username " +
                    "AND is_enabled = TRUE " +
                    "AND is_account_non_locked = TRUE " +
                    "AND is_account_non_expired = TRUE",
            nativeQuery = true)
    @Transactional
    Optional<User> findByEmailAndUserIsActive(@Param("username") String username);

    @Modifying
    @Query(value = "UPDATE users SET is_enabled = FALSE, " +
            "is_account_non_locked = FALSE WHERE id = :id",
            nativeQuery = true)
    @Transactional
    void deactivateUser(@Param("id") long id);

    @Modifying
    @Query(value = "UPDATE users SET is_enabled = TRUE, " +
            "is_account_non_locked = TRUE WHERE id = :id",
            nativeQuery = true)
    @Transactional
    void restoreEmployee(@Param("id") long id);

    @Modifying
    @Query(value = "UPDATE users SET roles = :roles WHERE id = :id", nativeQuery = true)
    @Transactional
    void changeRoles(@Param("id") long id, @Param("roles") String[] roles);

}
