package org.dnd.services.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpeedParser {
    public static int speedParser(String url) throws Exception {
        Document doc = Jsoup.connect(url).get();
        int speed = 0;
        Elements classElements = doc.select("p");
        for (Element element : classElements) {
            if (element.text().contains("Скорость")) {
                speed = Integer.parseInt(element.text().replaceAll("\\D+", ""));
                break;
            }
        }
        return speed;
    }
}