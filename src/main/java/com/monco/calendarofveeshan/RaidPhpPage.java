/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monco.calendarofveeshan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author stephen.williams@monco.info
 *
 * Entity class to contain everything on raid.php
 *
 */
public class RaidPhpPage {

    int page_id;
    List<RaidTarget> raidTargets;
    List<Guild> guilds;
    Date timeRetrieved;

    public RaidPhpPage(List<RaidTarget> raidTargets, List<Guild> guilds) {
        this.raidTargets = raidTargets;
        this.guilds = guilds;
        this.timeRetrieved = new Date();
        long secs = this.timeRetrieved.getTime() / 1000;
        this.page_id = (int) secs;
    }

    public int getPage_id() {
        return page_id;
    }

    public List<RaidTarget> getRaidTargets() {
        return raidTargets;
    }

    public void setRaidTargets(List<RaidTarget> raidTargets) {
        this.raidTargets = raidTargets;
    }

    public List<Guild> getGuilds() {
        return guilds;
    }

    public void setGuilds(List<Guild> guilds) {
        this.guilds = guilds;
    }

    public Date getTimeRetrieved() {
        return timeRetrieved;
    }

    //return changed raidtargets from the other (old) instance
    public List<RaidTarget> getChangedTargets(RaidPhpPage oldPage) {
        List<RaidTarget> changedTargets = new ArrayList();
        List<RaidTarget> oldRaidTargets = oldPage.getRaidTargets();
        for (int i = 0; i < this.raidTargets.size(); i++) {
            RaidTarget rtNew = this.raidTargets.get(i);
            RaidTarget rtOld = oldRaidTargets.get(i);
            //System.out.println("\nComparing "+rtNew+" against "+rtOld);
            if ( !rtNew.equals(rtOld) ) {
                changedTargets.add(rtOld);
                //System.out.print("CHANGE DETECTED!");
            }
            //else System.out.print("NO CHANGE!");
        }
        return changedTargets;
    }

    @Override
    public String toString() {
        String string = "\nRaidPhpPage " + page_id + ", " + timeRetrieved.getTime()
                + "\n\nTargets\n" + raidTargets
                + "\n\nGuilds\n" + guilds;
        return string;
    }

    @Override
    public boolean equals(Object obj) {//does not include timeRetrieved
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RaidPhpPage other = (RaidPhpPage) obj;
        if (!Objects.equals(this.raidTargets, other.raidTargets)) {
            return false;
        }
        if (!Objects.equals(this.guilds, other.guilds)) {
            return false;
        }
        return true;
    }

}
