package org.dnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RollDTO {
    private String username;
    private int amount;
    private int type;
    private int addition;
    private boolean each;
    private int result;
}