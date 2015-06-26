/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monco.calendarofveeshan;

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
    
    
    
    
}
