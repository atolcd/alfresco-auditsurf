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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.util.Assert;

import com.atolcd.alfresco.audit.cmr.AuditQueriesService;

public class ConnectionLogsGet extends DeclarativeWebScript implements InitializingBean {

	private AuditQueriesService auditQueriesService;

	public void setAuditQueriesService(AuditQueriesService auditQueriesService) {
		this.auditQueriesService = auditQueriesService;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(auditQueriesService);
	}

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		try {

			// Map that will be passed to the template
			Map<String, Object> model = new HashMap<String, Object>();

			// If there is at least a parameter
			if (req.getParameterNames().length > 1) {
				boolean noAdmin = false;
				int limit = -1;
				Calendar start = null, end = null;

				String noadminParam = req.getParameter("noadmin");
				if (noadminParam != null)
					noAdmin = Boolean.valueOf(noadminParam);

				String hasLimit = req.getParameter("limit");
				if (hasLimit != null)
					limit = Integer.parseInt(hasLimit);

				System.out.println("Limite : " + limit);

				String startDate = req.getParameter("start");
				String endDate = req.getParameter("end");
				if (startDate != null && endDate != null) {
					start = getDateFromString(startDate, "-");
					end = getDateFromString(endDate, "-");
				}

				List<Object[]> listres = auditQueriesService.getConnectionLogs("authenticate", start, end, noAdmin, limit);
				Map<Date, String[]> logs = new HashMap<Date, String[]>();

				for (int i = 0; i < listres.size(); i++) {
					String fail = (listres.get(i)[2]).toString();

					String[] tab = { (String) listres.get(i)[1], fail };
					logs.put((Date) listres.get(i)[0], tab);
				}

				// Insert into the map
				model.put("connectionLogs", logs.entrySet());
			}

			return model;
		} catch (Exception e) {
			throw new WebScriptException("[ConnectionLogsGet] Error in executeImpl function");
		}
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