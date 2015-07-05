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
public class Lockout {
    private Guild guild;
    private int remainingLockouts;

    public Lockout(Guild guild, int remainingLockouts) {
        this.guild = guild;
        this.remainingLockouts = remainingLockouts;
    }
    
    @Override
    public String toString() {
        return "\n"+ guild.getName() + ", " + remainingLockouts;
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
        final Lockout other = (Lockout) obj;
        if (!Objects.equals(this.guild, other.guild)) {
            return false;
        }
        if (this.remainingLockouts != other.remainingLockouts) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
