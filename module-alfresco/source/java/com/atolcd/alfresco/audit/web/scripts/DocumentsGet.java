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

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.util.Assert;

import com.atolcd.alfresco.audit.cmr.AuditQueriesService;

public class DocumentsGet extends DeclarativeWebScript implements InitializingBean {

	private AuditQueriesService auditQueriesService;

	// Setter
	public void setAuditQueriesService(AuditQueriesService auditQueriesService) {
		this.auditQueriesService = auditQueriesService;
	}

	// Verify that the setter have been called
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(auditQueriesService);
	}

	/**
	 * PRINCIPAL METHOD
	 * 
	 * @param req
	 *            Contains the parameters passed to webscript to address the
	 *            various functions and periods.
	 * 
	 * @param status
	 *            Web Script Status Records the outcome of a Web Script.
	 * 
	 */
	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		try {
			Map<String, Object> model = new HashMap<String, Object>();

			String period = req.getParameter("period");
			int paramDate = 0;
			if (req.getParameter("date") != null)
				paramDate = Integer.parseInt(req.getParameter("date"));
			GregorianCalendar calendar = DateInterval.setInterval(new Date(), period, paramDate);

			model.put("documentModifications", auditQueriesService.countCheckins(DateInterval.startEndPeriod(calendar, period), period)
					.entrySet());
			model.put("documentCreations", auditQueriesService.countCreateDocs(DateInterval.startEndPeriod(calendar, period), period)
					.entrySet());
			model.put("documentReadings", auditQueriesService.countReadDocs(DateInterval.startEndPeriod(calendar, period), period)
					.entrySet());

			return model;
		} catch (Exception e) {
			throw new WebScriptException("[DocumentsGet] Error in execute function");
		}
	}
}