/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monco.calendarofveeshan;

import java.util.Objects;

/**
 *
 * @author stephen.williams@monco.info
 */
public class Guild {
    private String name;
    private String raidClass;

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

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Guild other = (Guild) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.raidClass, other.raidClass)) {
            return false;
        }
        return true;
    }
    
    
    
}
