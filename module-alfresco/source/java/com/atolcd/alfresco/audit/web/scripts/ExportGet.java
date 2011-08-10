/**
 * Copyright (C) 2011 Atol Conseils et Développements.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA. **/

package com.atolcd.alfresco.audit.web.scripts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.util.Assert;

import com.atolcd.alfresco.audit.cmr.AuditQueriesService;
import com.atolcd.alfresco.audit.cmr.EnumFonctionnalite;

public class ExportGet extends DeclarativeWebScript implements InitializingBean {

	private AuditQueriesService auditQueriesService;
	private Calendar startDate;
	private Calendar endDate;

	// Setters
	public void setAuditQueriesService(AuditQueriesService auditQueriesService) {
		this.auditQueriesService = auditQueriesService;
	}

	public void setStartDate(Calendar start) {
		this.startDate = start;
	}

	public void setEndDate(Calendar end) {
		this.endDate = end;
	}

	// Verify that the setter have been called
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(auditQueriesService);
	}

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		// Retrieving parameters
		String start_date = req.getParameter("start");
		String end_date = req.getParameter("stop");

		// Map that will be passed to the template
		Map<String, Object> model = new HashMap<String, Object>();

		Map<String, String> params = new HashMap<String, String>();
		params.put("createddocs", req.getParameter("createddocs"));
		params.put("modifieddocs", req.getParameter("modifieddocs"));
		params.put("readdocs", req.getParameter("readdocs"));
		params.put("conn", req.getParameter("conn"));
		params.put("createduser", req.getParameter("createduser"));
		params.put("createdwork", req.getParameter("createdwork"));

		if (testParameters(start_date, end_date)) { // test parameters
			List<String> parameters = analyzeParameters(params);
			buildTree(parameters, model);
		}

		return model;

	}

	private void buildTree(List<String> parameters, Map<String, Object> model) {

		List<Functionality> functionalities = new ArrayList<Functionality>();

		for (int i = 0; i < parameters.size(); i++) {
			SortedMap<Date, Integer> resQuery = new TreeMap<Date, Integer>();

			switch (EnumFonctionnalite.valueOf(parameters.get(i))) {
			case createddocs:
				resQuery.putAll(auditQueriesService.countCreateDocs(new Calendar[] { startDate, endDate }, "none"));
				generateTree("createddocs", resQuery, functionalities);
				break;

			case modifieddocs:
				resQuery.putAll(auditQueriesService.countCheckins(new Calendar[] { startDate, endDate }, "none"));
				generateTree("modifieddocs", resQuery, functionalities);
				break;

			case readdocs:
				resQuery.putAll(auditQueriesService.countReadDocs(new Calendar[] { startDate, endDate }, "none"));
				generateTree("readdocs", resQuery, functionalities);
				break;

			case conn:
				resQuery.putAll(auditQueriesService.countConnections(new Calendar[] { startDate, endDate }, "none"));
				generateTree("conn", resQuery, functionalities);
				break;

			case createduser:
				resQuery.putAll(auditQueriesService.countCreateUsers(new Calendar[] { startDate, endDate }, "none"));
				generateTree("createduser", resQuery, functionalities);
				break;

			case createdwork:
				resQuery.putAll(auditQueriesService.countWorkflows(new Calendar[] { startDate, endDate }, "none"));
				generateTree("createdwork", resQuery, functionalities);
				break;
			}
		}

		model.put("functionalities", functionalities);

	}

	private void generateTree(String func, SortedMap<Date, Integer> res, List<Functionality> functionalities) {
		Set<Map.Entry<Date, Integer>> mapEntries = res.entrySet();
		Iterator<Map.Entry<Date, Integer>> it = mapEntries.iterator();
		List<Entry> entries = new ArrayList<Entry>();

		while (it.hasNext()) {
			Map.Entry<Date, Integer> mapEntry = it.next();
			entries.add(new Entry(mapEntry.getKey(), mapEntry.getValue()));
		}

		functionalities.add(new Functionality(func, entries));
	}

	private List<String> analyzeParameters(Map<String, String> params) {
		List<String> res = new ArrayList<String>();

		for (Map.Entry<String, String> e : params.entrySet()) {
			if (e.getValue() != null && e.getValue().equals("true"))
				res.add(e.getKey());
		}

		return res;
	}

	private boolean testParameters(String s_start, String s_end) {
		if (s_start != null && s_end != null) {

			Calendar start = getDateFromString(s_start, "-");
			Calendar end = getDateFromString(s_end, "-");

			if (start.compareTo(end) < 0) {
				setStartDate(start);
				setEndDate(end);
			} else {
				setStartDate(end);
				setEndDate(start);
			}

			return true;
		}

		return false;
	}

	private GregorianCalendar getDateFromString(String s_date, String separator) {
		String[] date = new String[3];
		int cpt = 0;

		java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(s_date, separator);
		while (tokenizer.hasMoreTokens()) {
			date[cpt] = tokenizer.nextToken().toString();
			cpt++;
		}

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Integer.parseInt(date[2]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[0]));

		return calendar;
	}

}
