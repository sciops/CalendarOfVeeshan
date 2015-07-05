/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monco.calendarofveeshan;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author stephen.williams@monco.info
 */
public class RaidPhpParser {

    //final String pagePath = "raidPHPPage.json";
    //final String killPath = "kill.json";
    final String URL = "https://www.project1999.com/raid.php";
    //final String URL = "http://127.0.0.1/Project%201999%20-%20Raid%20Policy.html";
    //list for storing all the RaidTargets on the page
    List<RaidTarget> raidTargets = new ArrayList<RaidTarget>();
    //list for storing all the guilds on the page
    List<Guild> guilds = new ArrayList<Guild>();
    //place to store the above lists
    RaidPhpPage rpp = new RaidPhpPage(raidTargets, guilds);

    public RaidPhpPage crawl() throws IOException {
        return crawl(URL);
    }

    public RaidPhpPage crawl(String url) throws IOException {
        if ((url == null) || (url == "")) {
            url = URL;
        }
        //fetch the document and map to DOM
        Document doc = Jsoup.connect(url).get();

        //Parse the guilds
        int height = 7;
        Element classCTable = selectTable(doc, "Class C Guilds");
        guilds.addAll(mapGuildTable(height, classCTable, "Class C"));
        height = 12;
        Element classRTable = selectTable(doc, "Class R Guilds");
        guilds.addAll(mapGuildTable(height, classRTable, "Class R"));

        //Parse the Kunark spawns table
        //get the table
        Element kunarkTable = selectTable(doc, "Kunark");
        //height of table, keep getting 8 here, not sure why, hardcoding instead.
        //int height = kunarkTable.childNodeSize();
        height = 7;
        raidTargets.addAll(mapSpawnTable(height, kunarkTable));

        //Parse the Planes spawns table
        Element planesTable = selectTable(doc, "Planes");
        height = 5;
        raidTargets.addAll(mapSpawnTable(height, planesTable));

        //Parse the Classic spawns table
        Element classicTable = selectTable(doc, "Classic");
        height = 3;
        raidTargets.addAll(mapSpawnTable(height, classicTable));

        return rpp;
    }

    public RaidPhpPage getRpp() {
        return rpp;
    }

    private Element selectTable(Document doc, String searchTerm) {
        //get the first cell in the document that contains the text
        Element cell = doc.select("td:containsOwn(" + searchTerm + ")").first();
        //get the parent table
        Element table = cell.parent().parent();
        return table;
    }

    private List<RaidTarget> mapSpawnTable(int height, Element table) {
        List<RaidTarget> raidTargets = new ArrayList<RaidTarget>();

        // table loop, skip the header row
        for (int row = 1; row < height; row++) {

            //zero based indexes, this gets the second row/tr (1) and the first column/td (0)
            String targetName = table.child(row).child(0).text();
            String targetClass = table.child(row).child(1).text();
            RaidTarget rt = new RaidTarget(targetName, targetClass);
            List<Lockout> targetLockouts = new ArrayList<Lockout>();

            //get target's lockouts
            String targetLOText = table.child(row).child(2).text();
            //System.out.println("lockOuts text = " + targetLOText);
            //break this up by semi-colons, result is an array of strings containing unsplit guild/lockout pairs e.g. "Taken (1)"
            List<String> guildLOPairs = Arrays.asList(targetLOText.split("\\s*;\\s*"));
            List<String> splitGuildLOPair = new ArrayList<String>();
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
                //find guild
                Guild guild = null;
                String guildName = splitGuildLOPair.get(0);
                guildName = remSpace(guildName);
                //System.out.println("GUILDNAME=<" + guildName + ">");
                for (Guild g : guilds) {
                    if (guildName.equals(g.getName())) {
                        guild = g;
                    }
                }
                //create and add a lockout to the list
                Lockout lockout = new Lockout(guild, LO);
                targetLockouts.add(lockout);
            }
            //debug LO list
            //for (Lockout l : targetLockouts) {System.out.println(l.toString());}
            rt.setLockouts(targetLockouts);
            raidTargets.add(rt);

        }//end kunark table loop

        return raidTargets;
    }

    //parse a list of guilds from the guild tables in the page
    private List<Guild> mapGuildTable(int height, Element table, String raidClass) {
        List<Guild> guilds = new ArrayList<Guild>();
        for (int row = 1; row < height; row++) {
            String guName = table.child(row).child(0).text();
            guName = remSpace(guName);
            guilds.add(new Guild(guName, raidClass));
        }
        return guilds;
    }

    //removes space from string, including this weird unicode whitespace
    private String remSpace(String string) {
        //string = StringUtils.deleteWhitespace(string);//https://stackoverflow.com/questions/5455794/removing-whitespace-from-strings-in-java
        string = string.replace(" ", "");
    	string = string.replace("\u00a0", "");//https://stackoverflow.com/questions/8501072/string-unicode-remove-char-from-the-string
        return string;
    }
    
    public String objToJson(Object obj) {
    	Gson gson = new Gson();
    	return gson.toJson(obj);
    }

    public String killsToJson(List<Kill> kills) throws IOException {
        Gson gson = new Gson();
        return gson.toJson(kills);
    }

    public List<Kill> jsonToKills(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<Kill>>() {
        }.getType());
    }

    public String pageToJson(RaidPhpPage page) throws IOException {
        Gson gson = new Gson();
        return gson.toJson(page);
    }

    public RaidPhpPage jsonToPage(String json) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<RaidPhpPage>() {
        }.getType());
    }
    
}
