package org.dnd.services.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class BMParser {
    public static List<String> BMParser(String url) {
        List<String> BMlevel = new ArrayList<>();
        String prevBonus = "";
        try {
            int levelIndex = -1;
            int bonusIndex = -1;
            Document doc = Jsoup.connect(url).get();
            Elements classElements;
            classElements = doc.select("tr.table_header>td");
            if (classElements.size() > 1) {
                for (int i = 0; i < classElements.size(); i++) {
                    Element col = classElements.get(i);
                    if (col.text().contains("Уровень")) {
                        levelIndex = i;
                    } else if (col.text().contains("Бонус мастерства")) {
                        bonusIndex = i;
                        break;
                    }
                }
            }
            classElements = doc.select("tr");
            for (Element element : classElements) {
                Elements cols = element.select("td");
                if (cols.size() > 1) {
                    Element levelCell = cols.get(levelIndex);
                    Element bonusCell = cols.get(bonusIndex);
                    String levelText = levelCell.text();
                    String bonusText = bonusCell.text();
                    if (bonusText.startsWith("+")) {
                        if (!bonusText.equals(prevBonus)) {
                            BMlevel.add(levelText);
                            prevBonus = bonusText;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при подключении к URL: " + e.getMessage());
        }
        return BMlevel;
    }
}
