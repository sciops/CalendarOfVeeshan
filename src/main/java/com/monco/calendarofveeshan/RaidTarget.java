/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monco.calendarofveeshan;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author stephen.williams@monco.info
 *
 * This entity class represents a row on one of the tables on raid.php
 */
public class RaidTarget {

    private String mobName;//represents the raid target's mobName cell
    private String rClass;//represents the next spawn class cell
    private List<Lockout> lockouts; //represents the lockouts cell

    private Date lastKillTime;//TODO:replace with Kill lastKill?
    private Date nxAvgSpawnTime;
    private Date windowOpen;
    private Date windowClose;

    private final int HOURS_7DAY = 24 * 7;
    private final int HOURS_3DAY = 24 * 3;
    private final int STD_RESPAWN_HOURS = HOURS_7DAY;
    private final int STD_RESPAWN_WINDOW = 8;

    public RaidTarget(String name, String nxSpawnClass) {
        this.mobName = name;
        this.rClass = nxSpawnClass;
    }

    public RaidTarget(String name, String nxSpawnClass, List<Lockout> lockouts) {
        this.mobName = name;
        this.rClass = nxSpawnClass;
        this.lockouts = lockouts;
    }

    public RaidTarget(String name, String nxSpawnClass, List<Lockout> lockouts, Date killTime) {
        this.mobName = name;
        this.rClass = nxSpawnClass;
        this.lockouts = lockouts;
        setTimes(killTime);
    }

    public RaidTarget(String name, String nxSpawnClass, List<Lockout> lockouts, Date killTime, int respawnHours, int respawnWindow) {
        this.mobName = name;
        this.rClass = nxSpawnClass;
        this.lockouts = lockouts;
        setTimes(killTime, respawnHours, respawnWindow);
    }

    public void setTimes(Date killTime) {
        setTimes(killTime, STD_RESPAWN_HOURS, STD_RESPAWN_WINDOW);
    }

    public void setTimes(Date killTime, int respawnHours) {
        setTimes(killTime, respawnHours, STD_RESPAWN_WINDOW);
    }

    public void setTimes(Date killTime, int respawnHours, int respawnWindow) {
        this.lastKillTime = killTime;
        nxAvgSpawnTime = DateUtils.addHours(killTime, respawnHours);
        windowClose = DateUtils.addHours(killTime, (respawnWindow / 2));
        windowOpen = DateUtils.addHours(killTime, (-1) * (respawnWindow / 2));
    }

    public void setLockouts(List<Lockout> lockouts) {
        this.lockouts = lockouts;
    }

    public String getName() {
        return mobName;
    }

    public String getrClass() {
        return rClass;
    }

    public Date getKillTime() {
        return lastKillTime;
    }

    public Date getNxAvgSpawnTime() {
        return nxAvgSpawnTime;
    }

    public Date getWindowOpen() {
        return windowOpen;
    }

    public Date getWindowClose() {
        return windowClose;
    }

    public List<Lockout> getLockouts() {
        return lockouts;
    }

    @Override
    public String toString() {
        return "\n" + mobName + ", " + rClass;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.mobName);
        hash = 71 * hash + Objects.hashCode(this.rClass);
        hash = 71 * hash + Objects.hashCode(this.lockouts);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RaidTarget other = (RaidTarget) obj;
        if (!Objects.equals(this.mobName, other.mobName)) {
            return false;
        }
        if (!Objects.equals(this.rClass, other.rClass)) {
            return false;
        }

        return true;
    }

}
