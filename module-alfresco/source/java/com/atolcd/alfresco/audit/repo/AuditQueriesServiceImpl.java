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

package com.atolcd.alfresco.audit.repo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.service.cmr.audit.AuditQueryParameters;
import org.alfresco.service.cmr.audit.AuditService;
import org.alfresco.service.cmr.repository.NodeRef;

import com.atolcd.alfresco.audit.cmr.AuditQueriesService;
import com.atolcd.alfresco.audit.web.scripts.DateInterval;

public class AuditQueriesServiceImpl /* extends HibernateDaoSupport */implements AuditQueriesService {
	private AuditService auditService;

	public AuditService getAuditService() {
		return auditService;
	}

	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}

	private static final int MAX = 5;

	// ** PUBLIC METHODS**//
	public List<Object[]> getConnectionLogs(final String method, final Calendar start, final Calendar end, final boolean noAdmin,
			final int maxdisplay) {
		return new ArrayList<Object[]>();
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public Map<Date, Integer> countConnections(final Calendar[] calendars, String period) {
		return countQuery("UserLogin", calendars, period);
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public Map<Date, Integer> countCreateUsers(final Calendar[] calendars, String period) {
		return countQuery("CreateUser", calendars, period);
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public Map<Date, Integer> countCreateDocs(final Calendar[] calendars, String period) {
		return countQuery("CreateFile", calendars, period);
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public Map<Date, Integer> countReadDocs(final Calendar[] calendars, String period) {
		return countQuery("ReadFile", calendars, period);
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public Map<Date, Integer> countCheckins(final Calendar[] calendars, String period) {
		return countQuery("ModifyFile", calendars, period);
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public Map<Date, Integer> countWorkflows(final Calendar[] calendars, String period) {
		return countQuery("CreateWorkflow", calendars, period);
	}

	/**
	 * This method returns the most read documents (max 5 results)
	 * 
	 * @return result of query : list of couples nodeId - count
	 */
	public List<Object[]> topRead() {
		return topQuery("ReadFile", MAX);
	}

	/**
	 * This method returns the most modified documents (max 5 results)
	 * 
	 * @return result of query : list of couples nodeId - count
	 */
	public List<Object[]> topModif() {
		return topQuery("ModifyFile", MAX);
	}

	/**
	 * This method returns the 5 most recent documents or folders
	 * 
	 * @return
	 */
	public List<Map<String, Object>> lastDocs() {
		return executeQuery("CreateFile", MAX);
	}

	/**
	 * This method returns the 5 last documents or files modified
	 * 
	 * @return result of query : list of couples nodeId - count
	 */
	public List<Map<String, Object>> lastModif() {
		// FIXME: groupby manual, fix the size of search at 40 * MAX
		List<Map<String, Object>> allModif = executeQuery("ModifyFile", MAX * 40);
		List<Map<String, Object>> lastModif = new ArrayList<Map<String, Object>>();

		// group by nodeRef
		Set<NodeRef> lastModifNodeRef = new HashSet<NodeRef>();

		int modifFound = 0;
		int iList = 0;

		while (modifFound <= MAX && iList < allModif.size()) {
			Object o = allModif.get(iList).get("value");
			if (!lastModifNodeRef.contains(o)) {
				lastModifNodeRef.add((NodeRef) o);
				lastModif.add(allModif.get(iList));
				modifFound++;
			}
			iList++;
		}

		return lastModif;
	}

	/**
	 * This method returns the 5 most recent users
	 * 
	 * @return result of query : list of couples nodeId - count
	 */
	public List<Map<String, Object>> lastUsers() {
		return executeQuery("CreateUser", MAX);
	}

	/**
	 * This method returns the list of users which have already connected
	 * 
	 * @return result of query : list of couples users - service
	 */
	public List<Map<String, Object>> usersNeverLog() {
		return executeQuery("UserLogin", 0);
	}

	// ** LOCAL METHODS **//

	/**
	 * 
	 * @param application
	 * @param max
	 * 
	 * @return result of query : list of map: date, user, applicationName, and
	 *         value.
	 */
	private List<Map<String, Object>> executeQuery(final String application, final int max) {
		AuditQueryParameters parameters = new AuditQueryParameters();
		parameters.setApplicationName(application);
		parameters.setForward(false);

		AuditQueryCallbackImpl auditQueryCallback = new AuditQueryCallbackImpl(true);
		getAuditService().auditQuery(auditQueryCallback, parameters, max);

		return auditQueryCallback.getEntries();
	}

	/**
	 * 
	 * @param application
	 * @param max
	 * 
	 * @return top query
	 */
	private List<Object[]> topQuery(final String application, final int max) {
		AuditQueryParameters parameters = new AuditQueryParameters();
		parameters.setApplicationName(application);

		AuditQueryCallbackTopImpl auditQueryCallback = new AuditQueryCallbackTopImpl(true);
		getAuditService().auditQuery(auditQueryCallback, parameters, 0);

		return auditQueryCallback.getEntries(max);
	}

	/**
	 * 
	 * @param application
	 * @param calendars
	 * @param period
	 * 
	 * @return count query by date
	 */
	private Map<Date, Integer> countQuery(final String application, final Calendar[] calendars, String period) {
		Long timestart, timeEnd;

		// Get long of date depending on period
		if (period.equals("day") || period.equals("none")) {
			GregorianCalendar calstart = (GregorianCalendar) calendars[0].clone();
			calstart.set(GregorianCalendar.HOUR_OF_DAY, 0);
			calstart.set(GregorianCalendar.MINUTE, 0);
			calstart.set(GregorianCalendar.SECOND, 0);
			calstart.set(GregorianCalendar.MILLISECOND, 0);

			GregorianCalendar calEnd;
			if (period.equals("day"))
				calEnd = (GregorianCalendar) calendars[0].clone();
			else
				calEnd = (GregorianCalendar) calendars[1].clone();

			calEnd.set(GregorianCalendar.HOUR_OF_DAY, 23);
			calEnd.set(GregorianCalendar.MINUTE, 59);
			calEnd.set(GregorianCalendar.SECOND, 59);
			calEnd.set(GregorianCalendar.MILLISECOND, 999);

			timestart = calstart.getTimeInMillis();
			timeEnd = calEnd.getTimeInMillis();
		} else {
			timestart = calendars[0].getTimeInMillis();
			timeEnd = calendars[1].getTimeInMillis();
		}

		// Set up the query and execute it
		AuditQueryParameters parameters = new AuditQueryParameters();
		parameters.setApplicationName(application);
		if (application.equals("UserLogin"))
			parameters.setForward(true);
		else
			parameters.setForward(false);
		parameters.setFromTime(timestart);
		parameters.setToTime(timeEnd);

		// AuditQueryCallbackCountImpl format data for DateInterval.createPeriod
		AuditQueryCallbackCountImpl auditQueryCallbackCount = new AuditQueryCallbackCountImpl(true, period, calendars);
		getAuditService().auditQuery(auditQueryCallbackCount, parameters, 0);

		return DateInterval.createPeriod(auditQueryCallbackCount.getEntries(), period, calendars);
	}
}
