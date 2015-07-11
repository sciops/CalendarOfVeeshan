package com.monco.calendarofveeshan;

import java.util.List;

public class KillForm {
	List<Kill> kills;
	String [] checkedKills;

	public List<Kill> getKills() {
		return kills;
	}

	public void setKills(List<Kill> kills) {
		this.kills = kills;
	}

	public String[] getCheckedKills() {
		return checkedKills;
	}

	public void setCheckedKills(String[] checkedKills) {
		this.checkedKills = checkedKills;
	}
	
}
