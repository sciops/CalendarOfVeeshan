/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monco.calendarofveeshan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author stephen.williams@monco.info
 *
 * This entity represents one killing of a raid target
 */
public class Kill {

    private String mobName;
    private Date killTime;
    private String killClass;
    private Guild killGuild;
    private boolean valid = true;
    private boolean earthquake = false;
    
    public Kill() {
    	mobName="Joe Dragon";
    	killClass="FFA";
    	killTime = new Date();
    }
    
    public Kill(String killClass, String mobName, Date killTime) {
    	this.mobName = mobName;
    	this.killClass = killClass;
    	this.killTime = killTime;
    }
    
    //this constructor uses information from the old oldTarget object
    //killTime comes from the new page's retrieval time.
    Kill(RaidTarget oldTarget, Date killTime) {
        this.mobName = oldTarget.getName();
        this.killTime = killTime;
        this.killClass = oldTarget.getrClass();//the class of the kill is what the old oldTarget said the class would be.
    }

    Kill(RaidTarget oldTarget, Date killTime, Guild guild) {
        this.killGuild = guild;
        this.mobName = oldTarget.getName();
        this.killTime = killTime;
        this.killClass = oldTarget.getrClass();
    }

    public boolean isEarthquake() {
        return earthquake;
    }

    public void setEarthquake(boolean earthquake) {
        this.earthquake = earthquake;
        if (earthquake) {
            this.valid = false;
        }
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    public String getMobName() {
        return mobName;
    }

    public Date getKillTime() {
        return killTime;
    }

    public String getKillClass() {
        return killClass;
    }

    public Guild getKillGuild() {
        return killGuild;
    }

    public void setMobName(String mobName) {
		this.mobName = mobName;
	}

	public void setKillTime(Date killTime) {
		this.killTime = killTime;
	}

	public void setKillClass(String killClass) {
		this.killClass = killClass;
	}

	public void setKillGuild(Guild killGuild) {
		this.killGuild = killGuild;
	}
	
	//construct a string to output the kill as a line in the wki clone
	public String getWkiLine() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM HH:mm");
		String date = sdf.format(killTime);
		String wkiLine = "[ " + killClass + " " + mobName + " ] " + date + "";
		return wkiLine;
	}
	
	public void setWkiLine(String wkiLine) throws ParseException {
		KillForm kf = new KillForm();
		Kill k = kf.wkiLineToKill(wkiLine);
		mobName = k.getMobName();
		killClass = k.getKillClass();
		killTime = k.getKillTime();
	}

	@Override
    public String toString() {
        return "\nKill{" + "mobName=" + mobName + ", killTime=" + killTime + ", killClass=" + killClass + ", killGuild=" + killGuild + '}';
    }

}
