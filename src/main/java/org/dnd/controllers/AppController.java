package org.dnd.controllers;

import lombok.AllArgsConstructor;
import org.dnd.dto.ChrDTO;
import org.dnd.dto.RollDTO;
import org.dnd.entities.DiceRoll;
import org.dnd.dto.UserDTO;
import org.dnd.entities.User;
import org.dnd.repository.ChrRepository;
import org.dnd.repository.RollRepository;
import org.dnd.services.RollService;
import org.dnd.services.UserDetailsService;
import org.dnd.services.parsers.BMParser;
import org.dnd.services.parsers.InfoParser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.dnd.services.AddService;
import org.dnd.entities.Chr;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("api/v1/apps")
@AllArgsConstructor
public class AppController {
    private AddService service;
    private RollService rollService;
    private RollRepository rollrepository;
    private ChrRepository chrRepository;
    private UserDetailsService AuthUser;

    @GetMapping("/welcome")
    public String welcome(Model model){
        model.addAttribute("username", AuthUser.getAuthName());
        return "welcome";
    }

    @PostMapping("/registration")
    public String registrationUserAdd(UserDTO user) {
        service.addUser(user);
        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("title", "пользователь");
        return "registration";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "пользователь");
        return "login";
    }

    @GetMapping("/rolldice")
    public String getRolls(Model model) {
        List<DiceRoll> rolls = rollrepository.getRollsByUsername(AuthUser.getAuthName());
        Collections.reverse(rolls);
        model.addAttribute("rolls", rolls);
        model.addAttribute("username", AuthUser.getAuthName());
        return "rolldice";
    }

    @PostMapping("/roll")
    public String RollDice(RollDTO roll) {
        service.addRoll(roll);
        rollService.deleteOldRolls();
        return "redirect:/rolldice";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String admin(Model model){
        model.addAttribute("username", AuthUser.getAuthName());
        return "admin";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Chr> chrs = chrRepository.getChrByUsername(AuthUser.getAuthName());
        model.addAttribute("chrs", chrs);
        model.addAttribute("username", AuthUser.getAuthName());
        return "list";
    }

    @GetMapping("/list/add")
    public String chrAdd(Model model) {
        model.addAttribute("username", AuthUser.getAuthName());
        model.addAttribute("clas", InfoParser.classparser().keySet());
        model.addAttribute("back", InfoParser.backgroundparser().keySet());
        model.addAttribute("races", InfoParser.raceparser().keySet());
        return "addChr";
    }

    @PostMapping("/list/add")
    public String ChrAdding(ChrDTO chr, @RequestParam("name") String name) throws Exception {
        service.addChr(chr);
        return "redirect:/list";
    }

    @GetMapping("/list/edit")
    public String сhrEdit(@RequestParam("name") String name, Model model) {
        Chr chr = chrRepository.findByCharname(name).orElseThrow();
        model.addAttribute("chr", chr);
        return "chr";
    }

    @PostMapping("/saveCharacter")
    @Transactional
    public String saveCharacter(@ModelAttribute("character") Chr character, Model model) {
        model.addAttribute("character", character);
        chrRepository.deleteByCharname(character.getCharname());
        character.setUsername(AuthUser.getAuthName());
        character.setMaxhp(character.getMaxhp() + (character.getLevel()-1)*(character.getHitDice()/2+1+ character.getConstitutionscore()-10)/2);
        chrRepository.save(character);
        return "redirect:/list";
    }
}