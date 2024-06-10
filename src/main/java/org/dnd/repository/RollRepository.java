package org.dnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.dnd.entities.DiceRoll;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RollRepository extends JpaRepository<DiceRoll, Long> {
    List<DiceRoll> findByUsername(String username);

    List<DiceRoll> getRollsByUsername(String username);
}