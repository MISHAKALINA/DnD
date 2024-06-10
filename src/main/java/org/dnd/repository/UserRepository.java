package org.dnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.dnd.entities.User;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}