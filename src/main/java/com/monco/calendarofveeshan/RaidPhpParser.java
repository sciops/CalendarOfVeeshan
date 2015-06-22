/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monco.calendarofveeshan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public void crawl() throws IOException {
        crawl(URL);
    }

    public void crawl(String url) throws IOException {
        if ((url == null) || (url == "")) {
            url = URL;
        }
        //fetch the document and map to DOM
        Document doc = Jsoup.connect(url).get();

        //list for storing all the RaidTargets on the page
        List<RaidTarget> raidTargets = new ArrayList();

        //Parse the Kunark spawns table
        //get the first cell that contains the text "Kunark"
        Element kunarkCell = doc.select("td:containsOwn(kunark)").first();
        //get the parent table
        Element kunarkTable = kunarkCell.parent().parent();

        //height of table, keep getting 8 here, not sure why, hardcoding instead.
        //int height = kunarkTable.childNodeSize();
        int height = 7;
        //kunark table loop, skip the header row
        for (int row = 1; row < height; row++) {

            //zero based indexes, this gets the second row/tr (1) and the first column/td (0)
            String targetName = kunarkTable.child(row).child(0).text();
            String targetClass = kunarkTable.child(row).child(1).text();
            //System.out.println("name=" + targetName+" class=" + targetClass);

            //get target's lockouts
            List<Lockout> targetLockouts = new ArrayList();
            String targetLOText = kunarkTable.child(row).child(2).text();
            //System.out.println("lockOuts text = " + targetLOText);
            //break this up by semi-colons, result is an array of strings containing unsplit guild/lockout pairs e.g. "Taken (1)"
            List<String> guildLOPairs = Arrays.asList(targetLOText.split("\\s*;\\s*"));
            List<String> splitGuildLOPair = new ArrayList();
            //loop to split all the pairs
            for (String guildLOPair : guildLOPairs) {
                //System.out.println("guildLOPair=" + guildLOPair);
                //split the pair
                splitGuildLOPair = Arrays.asList(guildLOPair.split("\\s*[(]\\s*"));
                //parse integer and ignore the extra parantheses
                int LO = Integer.parseInt(splitGuildLOPair.get(1).replaceAll("[\\D]", ""));
                //System.out.println("LO=" + LO);
                //create a new Guild instance to fill the trakLockouts.
                //TODO: first populate an array of Guild objects, search for the Guild, then insert here
                Guild guild = new Guild(splitGuildLOPair.get(0), "Class Whatever");
                //create and add a lockout to the list
                targetLockouts.add(new Lockout(guild, LO));
            }
            //debug LO list
            //for (Lockout l : targetLockouts) {System.out.println(l.toString());}
            RaidTarget rt = new RaidTarget(targetName, targetClass, targetLockouts);
            raidTargets.add(rt);
        }//kunark table loop

        
        
        
        System.out.println(raidTargets);

    }
    

    public void jsoupTest(String url) throws IOException {
        if ((url == null) || (url == "")) {
            url = URL;
        }
        Document doc = Jsoup.connect(url).get();
        Elements newsHeadlines = doc.select("#mp-itn b a");
        String output = "\nCrawlTest Headlines:\n";
        output += newsHeadlines.text();
        System.out.println(output);
    }

    public void crawlTest(String url) throws IOException {
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
        System.out.println("kunark cell text: " + output);

        //get Next Spawn cell, Kunark's sibling
        /*
         Element nextSpawnCell = kunarkCell.nextElementSibling();
         output = nextSpawnCell.text();
         System.out.println("next spawn text: "+output);
         */
        //get the rest of the siblings
        Element sib = kunarkCell.nextElementSibling();
        while (sib != null) {
            System.out.println("sib=" + sib.text());
            sib = sib.nextElementSibling();
        }

        //get Trak's name cell
        Element kunarkTable = kunarkCell.parent().parent();
        Element trakCell = kunarkTable.child(1).child(0);//zero based indexes, this gets the second row/tr (1) and the first column/td (0)
        String trakName = trakCell.text();
        System.out.println("trak cell text = " + trakName);

        //get Trak's class
        Element trakClassCell = kunarkTable.child(1).child(1);
        String trakClass = trakClassCell.text();

        //get Trak's trakLockouts
        List<Lockout> trakLockouts = new ArrayList();
        Element trakLockoutCell = kunarkTable.child(1).child(2);
        String lockoutsText = trakLockoutCell.text();
        System.out.println("lockOuts text = " + lockoutsText);
        List<String> guildLOPairs = Arrays.asList(lockoutsText.split("\\s*;\\s*"));//break this up by semi-colons
        List<String> splitGuildLOPair = new ArrayList();
        for (String guildLOPair : guildLOPairs) {
            System.out.println("guildLOPair=" + guildLOPair);
            splitGuildLOPair = Arrays.asList(guildLOPair.split("\\s*[(]\\s*"));
            int LO = Integer.parseInt(splitGuildLOPair.get(1).replaceAll("[\\D]", ""));
            System.out.println("LO=" + LO);
            //create a new Guild instance to fill the trakLockouts.
            //TODO: first populate an array of Guild objects, search for the Guild, then insert here
            Guild guild = new Guild(splitGuildLOPair.get(0), "Class Whatever");
            //create and add a lockout to the list
            trakLockouts.add(new Lockout(guild, LO));
        }

        //debug trakLockouts list
        for (Lockout l : trakLockouts) {
            System.out.println(l.toString());
        }

        RaidTarget trak = new RaidTarget(trakName, trakClass, trakLockouts);
        System.out.println(trak);

    }

    public static void main(String[] args) throws IOException {
        RaidPhpParser rpp = new RaidPhpParser();
        rpp.crawl();
    }
}
