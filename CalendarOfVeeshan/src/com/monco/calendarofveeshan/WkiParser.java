package com.monco.calendarofveeshan;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WkiParser {
    final String defURL = "http://whenkilledit.alwaysdata.net/";

    public KillForm crawl() throws IOException, ParseException {
        return crawl(defURL);
    }

    public KillForm crawl(String url) throws IOException, ParseException {
        if ((url == null) || (url == "")) {
            url = defURL;
        }
        //for returning an object with the array of kills
        KillForm kf = new KillForm();
        //fetch the document and map to DOM
        Document doc = Jsoup.connect(url).get();
        //https://stackoverflow.com/questions/7036332/jsoup-select-and-iterate-all-elements
        Elements body = doc.body().select("p");
        List<Kill> killList = new ArrayList<Kill>();
        String s;
        for (Element e : body) {
        	s = e.ownText();
        	//System.out.println("s:"+s);
        	killList.add(kf.wkiLineToKill(s));
        }
        
        kf.setCheckedKills(killList);
        kf.setKills(killList);
        return kf;
    }
}
