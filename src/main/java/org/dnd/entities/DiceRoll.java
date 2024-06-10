package org.dnd.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "rolls")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiceRoll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private int amount;
    private int type;
    private int addition;
    private boolean each;
    private int result;

}