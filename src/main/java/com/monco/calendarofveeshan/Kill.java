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

    private Date killTime;
    private String killClass;
    private String killGuild;
    private Date nxAvgSpawnTime;
    private String nxClass;
    

    public Date getKillTime() {
        return killTime;
    }

    public String getKillClass() {
        return killClass;
    }

    public String getKillGuild() {
        return killGuild;
    }

    public Date getNxAvgSpawnTime() {
        return nxAvgSpawnTime;
    }

    public String getNxClass() {
        return nxClass;
    }
    
    
}
