/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monco.calendarofveeshan;

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
    List<Lockout> lockouts;
    Date timeRetrieved;

    public RaidPhpPage(List<RaidTarget> raidTargets, List<Guild> guilds, List<Lockout> lockouts, Date timeRetrieved) {
        this.raidTargets = raidTargets;
        this.guilds = guilds;
        this.lockouts = lockouts;
        this.timeRetrieved = timeRetrieved;
    }

    public int getPage_id() {
        return page_id;
    }

    public void setPage_id(int page_id) {
        this.page_id = page_id;
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

    public List<Lockout> getLockouts() {
        return lockouts;
    }

    public void setLockouts(List<Lockout> lockouts) {
        this.lockouts = lockouts;
    }

    public Date getTimeRetrieved() {
        return timeRetrieved;
    }

    public void setTimeRetrieved(Date timeRetrieved) {
        this.timeRetrieved = timeRetrieved;
    }
    
    @Override
    public String toString() {
        String string = "\nRaidPhpPage " + timeRetrieved.getTime()
                + "\n\nTargets\n" + raidTargets
                + "\n\nGuilds\n" + guilds
                + "\n\nLockouts\n" + lockouts;
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
        if (!Objects.equals(this.lockouts, other.lockouts)) {
            return false;
        }
        return true;
    }
    
    

}
