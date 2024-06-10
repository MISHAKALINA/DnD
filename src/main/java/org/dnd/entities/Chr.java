package org.dnd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "chr")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    public String charname;
    private String clas;
    private String background;
    private String alignment;
    private String race;
    private int level;
    private int experiencepoints;
    private int BM;
    private int Strengthscore;
    private boolean strSafe;
    private int Dexterityscore;
    private boolean dexSafe;
    private int Constitutionscore;
    private boolean conSafe;
    private int Intelligencescore;
    private boolean intelijSafe;
    private int Wisdomscore;
    private boolean wisSafe;
    private int Charismascore;
    private boolean chrSafe;
    private List<Boolean> skillsProf;
    private int deathsuccesses;
    private int deathfails;
    private int ac;
    private int maxhp;
    private int curhp;
    private int temphp;
    private int hitDice;
    private String otherprofs;
    private int cp;
    private int sp;
    private int ep;
    private int gp;
    private int pp;
    private int speed;
    private String personality;
    private String ideals;
    private String bonds;
    private String flaws;
    private String features;
    private int skillscount;
    private String equip;
}
