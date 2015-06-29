/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monco.calendarofveeshan;

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



}
