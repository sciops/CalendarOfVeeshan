package com.monco.calendarofveeshan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KillForm {
	List<Kill> kills;//list of kills
	List<Kill> checkedKillsList;//list of verified kills
	String[] checkedKills;//string array of verified kills for default checked on boxes
	String checkedResult;//a long string of all the wkiLines separated by newlines

	public KillForm() {
		checkedKills = new String[] {};
	}

	public List<Kill> getKills() {
		return kills;
	}

	public void setKills(List<Kill> kills) {
		this.kills = kills;
	}

	public String[] getCheckedKills() {
		return checkedKills;
	}

	public List<Kill> getCheckedKillsList() {
		return checkedKillsList;
	}

	public void setCheckedKills(String[] checkedKills) throws ParseException {
		List<Kill> checkedKillsList = new ArrayList();
		String checkedResult = "";
		for (String s : checkedKills) {
			checkedKillsList.add(wkiLineToKill(s));
			checkedResult += "\n" + s;
		}
		this.checkedResult = checkedResult;
		this.checkedKills = checkedKills;
		this.checkedKillsList = checkedKillsList;
	}

	public void setCheckedKills(List<Kill> checkedKillsList) {
		String[] checkedKills = new String[checkedKillsList.size()];
		String checkedResult = "";
		int i = 0;
		for (Kill k : checkedKillsList) {
			checkedKills[i] = k.getWkiLine();
			checkedResult += "\n" + k.getWkiLine();
			i++;
		}
		this.checkedResult = checkedResult;
		this.checkedKills = checkedKills;
		this.checkedKillsList = checkedKillsList;
	}

	public Kill wkiLineToKill(String wkiLine) throws ParseException {
		int ob = wkiLine.indexOf("[");
		int cb = wkiLine.indexOf("]");
		// System.out.println("wkiLine:"+wkiLine+" ob:"+ob+" cb:"+cb);
		if ((cb == -1) || (ob == -1)) // not found
			return null;
		Kill kill = new Kill();
		String mobName;
		String killClass;
		// assign the kill class and mob name inside the brackets
		// i.e. "[ FFA Maestro ] 01-Jul 17:08"
		if (wkiLine.substring(ob + 2, ob + 5).equals("FFA")) {
			killClass = "FFA";
			mobName = wkiLine.substring(ob + 6, cb - 1);
		} else { // i.e. "[ Class R Maestro ] 01-Jul 17:08"
			killClass = wkiLine.substring(ob + 2, ob + 9);
			mobName = wkiLine.substring(ob + 10, cb - 1);
		}
		// System.out.println("New Kill:"+mobName+","+killClass);
		kill.setKillClass(killClass);
		kill.setMobName(mobName);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM HH:mm");
		String date_s = wkiLine.substring(cb + 1);
		Date d = formatter.parse(date_s);
		kill.setKillTime(d);
		return kill;
	}

	public String getCheckedResult() {
		return checkedResult;
	}

}
