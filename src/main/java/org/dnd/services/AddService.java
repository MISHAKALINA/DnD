package org.dnd.services;

import lombok.AllArgsConstructor;
import org.dnd.dto.ChrDTO;
import org.dnd.entities.Chr;
import org.dnd.repository.ChrRepository;
import org.dnd.services.parsers.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.dnd.dto.UserDTO;
import org.dnd.entities.User;
import org.dnd.repository.UserRepository;
import org.dnd.dto.RollDTO;
import org.dnd.entities.DiceRoll;
import org.dnd.repository.RollRepository;
import org.dnd.services.parsers.SpeedParser;

@Service
@AllArgsConstructor
public class AddService {
    private UserRepository repositoryUser;
    private RollRepository repositoryRoll;
    private ChrRepository repositoryChr;
    private PasswordEncoder passwordEncoder;
    private UserDetailsService AuthUser;


    public void addUser(UserDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        User userEntity = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
        repositoryUser.save(userEntity);
    }

    public void addRoll(RollDTO roll) {
        DiceRoll rollEntity = DiceRoll.builder()
                .username(AuthUser.getAuthName())
                .amount(roll.getAmount())
                .addition(roll.getAddition())
                .each(roll.isEach())
                .type(roll.getType())
                .result(RollService.rolling(roll.getAmount(), roll.getType(), roll.getAddition(), roll.isEach()))
                .build();
        repositoryRoll.save(rollEntity);
    }

    public void addChr(ChrDTO chr) throws Exception {
        String clasURL = InfoParser.classparser().get(chr.getClas());
        String raceURL = InfoParser.raceparser().get(chr.getRace());
        String backURL = InfoParser.backgroundparser().get(chr.getBackground());
        int HitDice = (int) ClassInfoParser.classinfoparser(clasURL).get("HitDice");
        chr.setLevel(1);
        Chr chrEntity = Chr.builder()
                .username(AuthUser.getAuthName())
                .charname(chr.getName())
                .clas(chr.getClas())
                .background(chr.getBackground())
                .alignment(chr.getAlignment())
                .race(chr.getRace())
                .level(chr.getLevel())
                .experiencepoints(0)
                .BM(2)
                .Strengthscore(chr.getStrength())
                .Dexterityscore(chr.getDexterity())
                .Constitutionscore(chr.getConstitution())
                .Intelligencescore(chr.getIntelligence())
                .Wisdomscore(chr.getWisdom())
                .Charismascore(chr.getCharisma())
                .deathfails(0)
                .deathsuccesses(0)
                .maxhp(HitDice+(chr.getConstitution()-10)/2)
                .curhp(HitDice+(chr.getConstitution()-10)/2)
                .temphp(0)
                .ac(10 + (chr.getDexterity()-10)/2)
                .hitDice(HitDice)
                .sp(0)
                .ep(0)
                .gp(0)
                .pp(0)
                .speed(SpeedParser.speedParser(raceURL))
                .otherprofs(GetProf.getProfString(chr))
                .features(ClassFeaturesParser.classinfoparser(clasURL).get(1))
                .ideals("")
                .bonds("")
                .flaws("")
                .features("")
                .skillscount((int) ClassInfoParser.classinfoparser(clasURL).get("SkillsCount"))
                .equip((String) BackParser.backParser(backURL).get("equipment"))
                .build();
        repositoryChr.save(chrEntity);
    }
}