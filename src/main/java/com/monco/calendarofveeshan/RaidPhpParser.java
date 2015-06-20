/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monco.calendarofveeshan;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author stephen.williams@monco.info
 */
public class RaidPhpParser {

    final String URL = "https://www.project1999.com/raid.php";

    public void crawlTest(String url) throws IOException {
        if ((url == null) || (url == "")) {
            url = URL;
        }
        Document doc = Jsoup.connect(url).get();
        Elements newsHeadlines = doc.select("#mp-itn b a");
        String output = "\nCrawlTest Headlines:\n";
        output += newsHeadlines.text();
        System.out.println(output);
    }

    public void crawl() throws IOException {
        crawl(URL);
    }

    public void crawl(String url) throws IOException {
        if ((url == null) || (url == "")) {
            url = URL;
        }
        Document doc = Jsoup.connect(url).get();
        String output = "\nTitle: " + doc.title();
        System.out.println(output);
        Element body = doc.body();
        //get the first node that contains the text "Kunark"
        Element kunarkCell = doc.select("td:containsOwn(kunark)").first();
        output = kunarkCell.text();
       
        System.out.println("text: "+output);

    }

    public static void main(String[] args) throws IOException {
        RaidPhpParser rpp = new RaidPhpParser();
        rpp.crawl();
    }
}
