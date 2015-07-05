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

import com.monco.calendarofveeshan.Kill;
import com.monco.calendarofveeshan.RaidPhpParser;
 
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
    public Kill kill() throws IOException {
    	//ObjectMapper mapper = new ObjectMapper();
    	//model.addAttribute("kills", mapper.writeValueAsString(kills));
        return kills.get(kills.size() - 1);
    }

    //expose all kills in a JSON format
    @RequestMapping("/kills")
    public List<Kill> getkills() throws IOException {
        return this.kills;
    }
    
    //accept a JSON and replace the kills list with it.
    @RequestMapping("/setkills")
    public String setkills(@RequestParam(value="json", defaultValue="Invalid.")String json) {
        if (json.equals("Invalid.")) return json;
        RaidPhpParser parser = new RaidPhpParser();
        this.kills = parser.jsonToKills(json);
        return "Done.";
    }

    //clone of http://whenkilledit.alwaysdata.net/
    @RequestMapping("/wkiclone")
    public String wkiclone() throws IOException {
        String output = "<html><head>";
        output+="<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />";
        output+="<meta http-equiv=\"Cache-Control\" content=\"no-store\" />";
        //output+="<link rel=\"shortcut icon\" type=\"image/png\" href=\"favicon.png\" />";
        output+="<title>When Killed It Clone</title>";
        output+="<style>body { background: #EBEBE8; } p { color: #6b6b6b; }</style>";
        output+="</head><body>";
        
        for (Kill kill : this.kills) {
            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat("dd-MMM HH:mm");
            String date = sdf.format(kill.getKillTime());
            output += "<p>[ " + kill.getKillClass() + " " + kill.getMobName() + " ] " + date+"</p>";
        }
        
        output+="</body></html>";

        return output;
    }
 
}
