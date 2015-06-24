/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monco.calendarofveeshan;

import java.util.List;

/**
 *
 * @author stephen.williams@monco.info
 */
public class Guild {
    private String name;
    private String raidClass;
    //private List<Lockout> lockouts;

    public Guild(String name, String raidClass) {
        this.name = name;
        this.raidClass = raidClass;
    }

    public String getName() {
        return name;
    }

    public String getRaidClass() {
        return raidClass;
    }


    public void setRaidClass(String raidClass) {
        this.raidClass = raidClass;
    }


    @Override
    public String toString() {
        return "\n" + name + ", "+ raidClass ;
    }
    
    
}
