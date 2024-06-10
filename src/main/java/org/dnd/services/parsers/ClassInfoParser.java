package org.dnd.services.parsers;

import org.dnd.DnDApplication;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.util.*;

public class ClassInfoParser {
    static int HitDice;
    static String ArmorProf;
    static String WeaponProf;
    static List<String> SaveRoll;
    static List<String> Skills;
    static int SkillsCount;
    public static Map classinfoparser(String url) {
        Map characterData = new HashMap();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements classElements;
            if (url.equals("https://dnd.su/class/93-monk/")) {
                classElements = doc.select("div.additionalInfo>span>div>p");
            } else {
                classElements = doc.select("div.additionalInfo>span>p");
            }
            for (Element element : classElements) {
                if (element.text().contains("Кость Хитов")){
                    HitDice = Integer.parseInt(element.text().replaceAll("[^0-9 ]", "").replaceAll(" +", " ").trim().substring(1)) ;
                    characterData.put("HitDice", HitDice);
                } else if (element.text().contains("Доспехи")) {
                    int index = element.text().indexOf(":");
                    ArmorProf = element.text().substring(index + 2);
                    characterData.put("ArmorProf", ArmorProf);
                } else if (element.text().contains("Оружие")) {
                    int index = element.text().indexOf(":");
                    WeaponProf = element.text().substring(index + 2);
                    characterData.put("WeaponProf", WeaponProf);
                } else if (element.text().contains("Спасброски")) {
                    SaveRoll = Arrays.asList(element.text().split(": ")[1].split(", "));
                    characterData.put("SaveRoll", SaveRoll);
                } else if (element.text().contains("Навыки")) {
                    if (element.text().contains("три")) {
                        SkillsCount = 3;
                    }
                    else {
                        SkillsCount = 2;
                    }
                    if (element.text().contains("любых")) {
                        Skills = Arrays.asList(element.text().split(": ")[1].split(", "));
                    }
                    else {
                        Skills = Arrays.asList(element.text().split(": ")[2].split(", "));
                    }
                    characterData.put("Skills", Skills);
                    characterData.put("SkillsCount", SkillsCount);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при подключении к URL: " + e.getMessage());
        }
        return characterData;



    }
}
