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


public class InfoParser {

    public static Map<String, String> classparser() {
        String url = "https://dnd.su/class/";
        Map<String, String> classMap = new HashMap<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements classElements = doc.select("a.tile-wrapper.class");
            for (Element element : classElements) {
                Elements articleTitleElements = element.select("span.article_title");
                Elements articleSourceElements = element.select("span.article_source");
                if (articleSourceElements.text().trim().equals("Player's handbook")) {
                    String className = articleTitleElements.text();
                    String classSource = articleSourceElements.text();
                    classMap.put(articleTitleElements.text(), "https://dnd.su/" + element.attr("href"));
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при подключении к URL: " + e.getMessage());
        }
        return classMap;
    }

    public static Map<String, String> backgroundparser() {
        String url = "https://dnd.su/backgrounds/";
        Map<String, String> backgroundsMap = new HashMap<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements listItems = doc.select("div.list-item__spell.for_filter[data-source='102']");
            for (Element listItem : listItems) {
                Element titleElement = listItem.select("div.list-item-title").first();
                String backgroundName = titleElement.text();
                String backgroundUrl = listItem.select("div>a").attr("href");
                backgroundsMap.put(backgroundName, "https://dnd.su/" + backgroundUrl);
            }

        } catch (IOException e) {
            System.out.println("Ошибка при подключении к URL: " + e.getMessage());
        }
        return backgroundsMap;
    }

    public static Map<String, String> raceparser() {
        String url = "https://dnd.su/race/";
        Map<String, String> racesMap = new HashMap<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements classElements = doc.select("a.tile-wrapper.race");
            for (Element element : classElements) {
                Elements articleTitleElements = element.select("span.article_title");
                Elements articleSourceElements = element.select("span.article_source");
                if (articleSourceElements.text().trim().equals("PH")) {
                    String raceName = articleTitleElements.text();
                    String raceUrl = "https://dnd.su/" + element.attr("href");
                    racesMap.put(raceName, raceUrl);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при подключении к URL: " + e.getMessage());
        }
        return racesMap;
    }
}