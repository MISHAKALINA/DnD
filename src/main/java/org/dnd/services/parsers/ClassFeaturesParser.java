package org.dnd.services.parsers;

import org.dnd.DnDApplication;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClassFeaturesParser {
    public static Map<Integer, String> classinfoparser(String url) {
        Map<Integer, String> featureMap = new HashMap<>();
        try {
            int levelIndex = -1;
            int skillIndex = -1;
            Document doc = Jsoup.connect(url).get();
            Elements classElements;
            classElements = doc.select("tr.table_header>td");
            System.out.println(-1);
            if (classElements.size() > 1) {
                for (int i = 0; i < classElements.size(); i++) {
                    Element col = classElements.get(i);
                    if (col.text().contains("Уровень")) {
                        levelIndex = i;
                    } else if (col.text().contains("Умения")) {
                        skillIndex = i;
                        break;
                    }
                }
            }
            classElements = doc.select("table.class_table>tr");
            System.out.println(skillIndex);
            for (Element element : classElements) {
                Elements cols = element.select("td");
                if (cols.size() > 2 && !cols.get(levelIndex).text().contains("Уровень")) {
                    Element level = cols.get(levelIndex);
                    Element featureText = cols.get(skillIndex);
                    int levelInt = Integer.parseInt(level.text());
                    featureMap.put(levelInt, featureText.text());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при подключении к URL: " + e.getMessage());
        }
        return featureMap;
    }

}