/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monco.calendarofveeshan;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author stephen.williams@monco.info
 */
public class RaidTarget {

    private String name;
    private String nxSpawnClass;
    private Date killTime;
    private Date nxAvgSpawnTime;
    private Date windowOpen;
    private Date windowClose;

    private final int HOURS_7DAY = 24 * 7;
    private final int HOURS_3DAY = 24 * 3;
    private final int STD_RESPAWN_HOURS = HOURS_7DAY;
    private final int STD_RESPAWN_WINDOW = 8;

    public RaidTarget(String name, String nxSpawnClass, Date killTime) {
        this.name = name;
        this.nxSpawnClass = nxSpawnClass;
        setTimes(killTime);
    }

    public RaidTarget(String name, String nxSpawnClass, Date killTime, int respawnHours, int respawnWindow) {
        this.name = name;
        this.nxSpawnClass = nxSpawnClass;
        setTimes(killTime,respawnHours,respawnWindow);
    }

    public void setTimes(Date killTime) {
        setTimes(killTime, STD_RESPAWN_HOURS, STD_RESPAWN_WINDOW);
    }

    public void setTimes(Date killTime, int respawnHours) {
        setTimes(killTime, respawnHours, STD_RESPAWN_WINDOW);
    }

    public void setTimes(Date killTime, int respawnHours, int respawnWindow) {
        this.killTime = killTime;
        nxAvgSpawnTime = DateUtils.addHours(killTime, respawnHours);
        windowClose = DateUtils.addHours(killTime, (respawnWindow / 2));
        windowOpen = DateUtils.addHours(killTime, (-1) * (respawnWindow / 2));
    }

    public String getName() {
        return name;
    }

    public String getNxSpawnClass() {
        return nxSpawnClass;
    }

    public Date getKillTime() {
        return killTime;
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

}
