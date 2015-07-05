/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monco.calendarofveeshan;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author stephen.williams@monco.info
 */
@RestController
public class KillController {

    @RequestMapping("/lastkill")
    public Kill kill() throws IOException {
        RaidPhpParser parser = new RaidPhpParser();

        List<Kill> kills = parser.retrieveKills();
        Kill kill = kills.get(kills.size() - 1);
        return kill;
    }

    @RequestMapping("/kills")
    public List<Kill> kills() throws IOException {
        System.out.println("KillController - kill() method");
        RaidPhpParser parser = new RaidPhpParser();

        List<Kill> kills = parser.retrieveKills();
        return kills;
    }

    @RequestMapping("/wkiclone")
    public String wkiclone() throws IOException {
        System.out.println("KillController - kill() method");
        RaidPhpParser parser = new RaidPhpParser();

        List<Kill> kills = parser.retrieveKills();
        String output = "<html><head>";
        output+="<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />";
        output+="<meta http-equiv=\"Cache-Control\" content=\"no-store\" />";
        //output+="<link rel=\"shortcut icon\" type=\"image/png\" href=\"favicon.png\" />";
        output+="<title>When Killed It Clone</title>";
        output+="<style>body { background: #EBEBE8; } p { color: #6b6b6b; }</style>";
        output+="</head><body>";
        
        for (Kill kill : kills) {
            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat("dd-MMM HH:mm");
            String date = sdf.format(kill.getKillTime());
            output += "<p>[ " + kill.getKillClass() + " " + kill.getMobName() + " ] " + date+"</p>";
        }
        
        output+="</body></html>";

        return output;
    }

}
