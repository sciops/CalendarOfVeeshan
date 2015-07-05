package com.monco.calendarofveeshan.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.monco.calendarofveeshan.Kill;
import com.monco.calendarofveeshan.RaidPhpParser;
 
//controller for managing requests for kill list information
@RestController
public class KillController {
    List<Kill> kills;
	//DI via Spring
	String message;
 
	@RequestMapping(value="/hello/{name}", method = RequestMethod.GET)
	public String getKill(@PathVariable String name, ModelMap model) {
 
		model.addAttribute("kill", name);
		model.addAttribute("message", this.message);
 
		//return to jsp page, configured in mvc-dispatcher-servlet.xml, view resolver
		return "list";
 
	}
 
	public void setMessage(String message) {
		this.message = message;
	}
	
	//just return the last kill in the list
    @RequestMapping("/lastkill")
    public String kill(ModelMap model) throws IOException {
    	Kill kill = kills.get(kills.size() - 1);
    	Gson gson = new Gson();
    	String json = gson.toJson(kill);
    	model.addAttribute("lastkill", json);
        return "lastkill";
    }

    //expose all kills in a JSON format
    @RequestMapping("/kills")
    public String getkills(ModelMap model) throws IOException {
    	RaidPhpParser parser = new RaidPhpParser();
    	String json = parser.killsToJson(kills);
    	model.addAttribute("kills", json);   
        return "kills";
    }
    
    //accept a JSON and replace the kills list with it.
    //see comment at bottom of this document for a test URL w/ json to load data
    @RequestMapping("/setkills")
    public String setkills(@RequestParam(value="json", defaultValue="Invalid.")String json) {
        if (json.equals("Invalid.")) return json;
        RaidPhpParser parser = new RaidPhpParser();
        this.kills = parser.jsonToKills(json);
        return "Done.";
    }

    //clone of http://whenkilledit.alwaysdata.net/
    //TODO: handle null case for kills
    @RequestMapping("/wkiclone")
    public String wkiclone(ModelMap model) throws IOException {
        String output = "";
        
        for (Kill kill : this.kills) {
            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat("dd-MMM HH:mm");
            String date = sdf.format(kill.getKillTime());
            output += "<p>[ " + kill.getKillClass() + " " + kill.getMobName() + " ] " + date+"</p>";
        }
        
        model.addAttribute("wkicbody",output);

        return "wkiclone";
    }
    
    //clone of https://www.project1999.com/raid.php
    @RequestMapping("/raidclone")
    public String raidclone(ModelMap model) throws IOException {
        String output = "";
        
        output+="TEST RAID BODY";
        
        model.addAttribute("raidbody",output);

        return "raid";
    }

    
 
}

//see setkills()
//http://localhost:8888/setkills?json=[{%22mobName%22:%22Trakanon%22,%22killTime%22:%22Jul%201,%202015%205:01:03%20PM%22,%22killClass%22:%22Class%20R%22},{%22mobName%22:%22Trakanon%22,%22killTime%22:%22Jul%201,%202015%205:06:12%20PM%22,%22killClass%22:%22Class%20R%22},{%22mobName%22:%22Lord%20Nagafen%22,%22killTime%22:%22Jul%201,%202015%205:08:43%20PM%22,%22killClass%22:%22Class%20C%22},{%22mobName%22:%22Talendor%22,%22killTime%22:%22Jul%201,%202015%205:09:25%20PM%22,%22killClass%22:%22Class%20R%22},{%22mobName%22:%22Venril%20Sathir%22,%22killTime%22:%22Jul%201,%202015%205:09:25%20PM%22,%22killClass%22:%22Class%20R%22}]
