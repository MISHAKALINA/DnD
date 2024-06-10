package org.dnd.services.parsers;


import org.dnd.DnDApplication;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackParser {

    public static Map<String, Object> backParser(String url) throws Exception {
        Document doc = Jsoup.connect(url).get();
        Map<String, Object> result = new HashMap<>();
        Elements classElements = doc.select("p");
        List<String> skills = new ArrayList<>();
        for (Element element : classElements) {
            if (element.text().contains("Владение навыками")) {
                for (Element skillElement : element.select("span[tooltip-for]")) {
                    skills.add(skillElement.text());
                }
                result.put("skills", skills);
            } else if (element.text().contains("Владение инструментами")) {
                result.put("toolsProf", element.text());
            } else if (element.text().contains("Снаряжение")) {
                result.put("equipment", element.text());
            }
        }
        result.put("skill", doc.select("h3.smallSectionTitle").get(1).text());
        return result;
    }
}

