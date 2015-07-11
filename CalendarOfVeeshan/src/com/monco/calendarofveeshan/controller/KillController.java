package com.monco.calendarofveeshan.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.monco.calendarofveeshan.Guild;
import com.monco.calendarofveeshan.Kill;
import com.monco.calendarofveeshan.KillForm;
import com.monco.calendarofveeshan.Lockout;
import com.monco.calendarofveeshan.RaidPhpPage;
import com.monco.calendarofveeshan.RaidPhpParser;
import com.monco.calendarofveeshan.RaidTarget;
import com.monco.calendarofveeshan.validator.*;

//controller for managing requests for kill list information
@RestController
public class KillController {
	List<Kill> kills;
	List<Kill> curatedKills;
	// DI via Spring
	String message;

	@RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
	public String getKill(@PathVariable String name, ModelMap model) {

		model.addAttribute("kill", name);
		model.addAttribute("message", this.message);

		// return to jsp page, configured in mvc-dispatcher-servlet.xml, view
		// resolver
		return "list";

	}

	public void setMessage(String message) {
		this.message = message;
	}

	// just return the last kill in the list
	@RequestMapping("/lastkill")
	public String kill(ModelMap model) throws IOException {
		Kill kill = kills.get(kills.size() - 1);
		Gson gson = new Gson();
		String json = gson.toJson(kill);
		model.addAttribute("lastkill", json);
		return "lastkill";
	}

	// expose all kills in a JSON format
	@RequestMapping("/kills")
	public String getkills(ModelMap model) throws IOException {
		RaidPhpParser parser = new RaidPhpParser();
		String json = parser.killsToJson(kills);
		model.addAttribute("kills", json);
		return "kills";
	}

	// accept a JSON and replace the kills list with it.
	// see comment at bottom of this document for a test URL w/ json to load
	// data
	@RequestMapping("/setkills")
	public String setkills(
			@RequestParam(value = "json", defaultValue = "Invalid.") String json) {
		if (json.equals("Invalid."))
			return json;
		RaidPhpParser parser = new RaidPhpParser();
		this.kills = parser.jsonToKills(json);
		return "Done.";
	}

	// clone of http://whenkilledit.alwaysdata.net/
	// TODO: handle null case for kills
	@RequestMapping("/wkiclone")
	public String wkiclone(ModelMap model) throws IOException {
		String output = "";

		for (Kill kill : this.kills) {
			output += "\n<p>" + kill.getWkiLine() + "</p>";
		}

		model.addAttribute("wkicbody", output);

		return "wkiclone";
	}

	// clone of https://www.project1999.com/raid.php
	@RequestMapping("/raidclone")
	public String raidclone(ModelMap model) throws IOException {
		// injection test, text above the tables
		String output = "";
		model.addAttribute("raidbody", output);

		// grab a page via crawl
		// TODO:grab from persistence instead
		RaidPhpParser parser = new RaidPhpParser();
		RaidPhpPage page = parser.crawl();
		List<RaidTarget> raidtargets = page.getRaidTargets();
		List<Guild> guilds = page.getGuilds();

		// inject targets to target tables
		String row = "";
		String raidclass = "";
		String kunarkRows = "";
		String planesRows = "";
		String classicRows = "";

		int i = 0;
		for (RaidTarget rt : raidtargets) {
			// assemble string for lockouts cell
			List<Lockout> los = rt.getLockouts();
			String losCell = "";
			for (Lockout lo : los) {
				losCell += lo.getGuild().getName() + " ("
						+ lo.getRemainingLockouts() + "); ";
			}
			// assemble cell for raid class
			raidclass = rt.getrClass();
			if (raidclass.equals("FFA"))
				raidclass = "green'><i>" + raidclass + "</i>";
			if (raidclass.equals("Class R"))
				raidclass = "blue'>" + raidclass + "";
			if (raidclass.equals("Class C"))
				raidclass = "red'>" + raidclass + "";
			raidclass = "<font color='" + raidclass + "</font color>";
			// assemble row
			row = "\n<tr>\n\t<td class=\"alt1\" align=\"left\" width=\"100\"><b>"
					+ rt.getName()
					+ "</b></td>\n\t<td class=\"alt1\" width=\"75\"><b>"
					+ raidclass
					+ "</b></td>\n\t<td class=\"alt1\"><i>"
					+ losCell + "</i></td>\n</tr>";
			// assign row to table, for now just done iteratively, relies on
			// same order
			// TODO: add a var to page descibing which table contains it
			if (i < 6)
				kunarkRows += row;
			else if (i < 10)
				planesRows += row;
			else if (i < 12)
				classicRows += row;
			i++;
		}
		// push rows to the model
		model.addAttribute("kunarktargets", kunarkRows);
		model.addAttribute("planestargets", planesRows);
		model.addAttribute("classictargets", classicRows);

		String cguildrows = "";
		String rguildrows = "";

		for (Guild guild : guilds) {
			if (guild.getRaidClass().equals("Class C"))
				cguildrows += "\n<tr>\n<td class=\"alt1\" align=\"left\" width=\"100\"><font color='red'><b>"
						+ guild.getName() + "</b></font color></td></tr>";
			if (guild.getRaidClass().equals("Class R"))
				rguildrows += "\n<tr>\n<td class=\"alt1\" align=\"left\" width=\"100\"><font color='blue'><b>"
						+ guild.getName() + "</b></font color></td></tr>";
			;
		}

		// push guilds to the model
		model.addAttribute("cguilds", cguildrows);
		model.addAttribute("rguilds", rguildrows);

		return "raid";
	}


	// http://www.mkyong.com/spring-mvc/spring-mvc-form-handling-annotation-example/
	//KillValidator killValidator;
	
	//@Autowired
	public KillController(
			//KillValidator killValidator
			) {
		//this.killValidator = killValidator;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("kForm") KillForm killform,
			BindingResult result, SessionStatus status) {

		//killValidator.validate(killform, result);

		if (result.hasErrors()) {
			// if validator failed
			return "kform";
		} else {
			status.setComplete();
			// form success
			return "ksuccess";
		}
	}

	@RequestMapping(value = "/killsform", method = RequestMethod.GET)
	public String initForm(ModelMap model) {

		KillForm form = new KillForm();
		//default checked values
		form.setCheckedKills(new String[] { "Default value here" });

		// command object
		model.addAttribute("kForm", new KillForm());

		// return form view
		return "kform";
	}

	@ModelAttribute("formList")
	public List<String> populateFormList() {

		// Data referencing for checkboxes
		List<String> formList = new ArrayList<String>();
		if (kills!=null)
		for (Kill kill : kills) {
			formList.add("\n"+kill.getWkiLine());
		}
		else formList.add("ERROR: KILL LIST IS NULL");
		return formList;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

}

// example json for import, see setkills()
// http://localhost:8888/setkills?json=[{%22mobName%22:%22Trakanon%22,%22killTime%22:%22Jul%201,%202015%205:01:03%20PM%22,%22killClass%22:%22Class%20R%22},{%22mobName%22:%22Trakanon%22,%22killTime%22:%22Jul%201,%202015%205:06:12%20PM%22,%22killClass%22:%22Class%20R%22},{%22mobName%22:%22Lord%20Nagafen%22,%22killTime%22:%22Jul%201,%202015%205:08:43%20PM%22,%22killClass%22:%22Class%20C%22},{%22mobName%22:%22Talendor%22,%22killTime%22:%22Jul%201,%202015%205:09:25%20PM%22,%22killClass%22:%22Class%20R%22},{%22mobName%22:%22Venril%20Sathir%22,%22killTime%22:%22Jul%201,%202015%205:09:25%20PM%22,%22killClass%22:%22Class%20R%22}]
