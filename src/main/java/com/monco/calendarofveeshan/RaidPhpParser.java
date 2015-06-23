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
import java.util.NavigableSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
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

    //list for storing all the RaidTargets on the page
    List<RaidTarget> raidTargets = new ArrayList();
    //list for storing all the guilds on the page
    List<Guild> guilds = new ArrayList() {
    };
    //list of lockouts
    List<Lockout> lockouts = new ArrayList();

    public void crawl() throws IOException {
        crawl(URL);
    }

    public void crawl(String url) throws IOException {
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

        //debug guilds
        String outputGuilds = "";
        for (Guild g : guilds) {
            outputGuilds += "\n" + g.getName() + ", " + g.getRaidClass();
        }
        System.out.println(outputGuilds);

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
        Element classicTable = selectTable(doc, "Planes");
        height = 3;
        raidTargets.addAll(mapSpawnTable(height, classicTable));

        //debug raidTargets
        String outputTargets = "";
        for (RaidTarget rt : raidTargets) {
            outputTargets += "\n" + rt.getName() + ", " + rt.getNxSpawnClass() + ", " + rt.getLockouts();
        }
        System.out.println(outputTargets);

    }

    private Element selectTable(Document doc, String searchTerm) {
        //get the first cell in the document that contains the text
        Element cell = doc.select("td:containsOwn(" + searchTerm + ")").first();
        //get the parent table
        Element table = cell.parent().parent();
        return table;
    }

    private List<RaidTarget> mapSpawnTable(int height, Element table) {
        List<RaidTarget> raidTargets = new ArrayList();

        // table loop, skip the header row
        for (int row = 1; row < height; row++) {

            //zero based indexes, this gets the second row/tr (1) and the first column/td (0)
            String targetName = table.child(row).child(0).text();
            String targetClass = table.child(row).child(1).text();
            List<Lockout> targetLockouts = new ArrayList();

            //get target's lockouts
            String targetLOText = table.child(row).child(2).text();
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
                lockouts.add(lockout);
                targetLockouts.add(lockout);
            }
            //debug LO list
            //for (Lockout l : targetLockouts) {System.out.println(l.toString());}
            RaidTarget rt = new RaidTarget(targetName, targetClass, targetLockouts);
            raidTargets.add(rt);
        }//kunark table loop

        return raidTargets;
    }

    private List<Guild> mapGuildTable(int height, Element table, String raidClass) {
        List<Guild> guilds = new ArrayList();
        for (int row = 1; row < height; row++) {
            String guName = table.child(row).child(0).text();
            guName = remSpace(guName);
            guilds.add(new Guild(guName, raidClass));
        }
        return guilds;
    }

    private String remSpace(String string) {
        string = StringUtils.deleteWhitespace(string);//https://stackoverflow.com/questions/5455794/removing-whitespace-from-strings-in-java
        string = string.replace("\u00a0", "");//https://stackoverflow.com/questions/8501072/string-unicode-remove-char-from-the-string
        return string;
    }

    public static void main(String[] args) throws IOException {
        RaidPhpParser rpp = new RaidPhpParser();
        rpp.crawl();
    }
}
