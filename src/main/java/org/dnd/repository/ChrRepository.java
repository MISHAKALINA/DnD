package org.dnd.repository;

import org.dnd.entities.Chr;
import org.dnd.entities.DiceRoll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChrRepository extends JpaRepository<Chr, Long> {
    Optional<Chr> findByUsername(String username);

    Optional<Chr> findByCharname(String username);
    Optional<Chr> deleteByCharname(String charname);
    List<Chr> getChrByUsername(String username);
}