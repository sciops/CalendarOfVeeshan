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
        //fetch the document and map to DOM
        Document doc = Jsoup.connect(url).get();
        
        //testing jsoup, print the html Title tag
        String output = "\nTitle: " + doc.title();
        System.out.println(output);
        
        //get the first cell that contains the text "Kunark"
        Element kunarkCell = doc.select("td:containsOwn(kunark)").first();
        output = kunarkCell.text();
        System.out.println("kunark cell text: "+output);
        
        //get Next Spawn cell, Kunark's sibling
        /*
        Element nextSpawnCell = kunarkCell.nextElementSibling();
        output = nextSpawnCell.text();
        System.out.println("next spawn text: "+output);
        */
                
        //get the rest of the siblings
        Element sib = kunarkCell.nextElementSibling();
        while (sib != null) {
            System.out.println("sib="+sib.text());
            sib = sib.nextElementSibling();
        }
        
        Element kunarkTable = kunarkCell.parent().parent();
        Element trakCell = kunarkTable.child(1).child(0).child(0);
        
        System.out.println("trak cell text = "+trakCell.text());
        
        
    }

    public static void main(String[] args) throws IOException {
        RaidPhpParser rpp = new RaidPhpParser();
        rpp.crawl();
    }
}
