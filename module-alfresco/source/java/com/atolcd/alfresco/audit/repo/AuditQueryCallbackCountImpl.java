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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.service.cmr.audit.AuditService.AuditQueryCallback;

/***
 * 
 * @author
 * 
 */

public class AuditQueryCallbackCountImpl implements AuditQueryCallback {
	private final boolean valuesRequired;
	private List<Object[]> entries;
	private String period;
	private Calendar[] calendars;

	private Set<String> userLog;

	public AuditQueryCallbackCountImpl(boolean valuesRequired, String period, Calendar[] calendars) {
		super();
		this.valuesRequired = valuesRequired;
		this.period = period;
		this.calendars = calendars;
		this.entries = new ArrayList<Object[]>();
		this.userLog = new HashSet<String>();

		if (period.equals("day") || period.equals("year")) {
			int nb = period.equals("day") ? 23 : 11;
			for (int i = 0; i <= nb; i++) {
				Object[] o = { (Integer) i, (Integer) 0 };
				entries.add(o);
			}
		} else {
			for (GregorianCalendar iCal = (GregorianCalendar) calendars[0].clone(); iCal.compareTo(calendars[1]) <= 0; iCal.add(
					GregorianCalendar.DATE, 1)) {
				Object obj[] = { (Date) iCal.getTime(), (Integer) 0 };
				entries.add(obj);
			}
		}
	}

	/**
	 * This method increases the table depending on the period
	 * 
	 */
	private void addEntry(Date newEntry) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(newEntry);

		if (period.equals("day") || period.equals("year")) {
			int i = period.compareTo("day") == 0 ? calendar.get(GregorianCalendar.HOUR_OF_DAY) : calendar.get(GregorianCalendar.MONTH);
			Object entryAdd[] = entries.get(i);
			entryAdd[1] = (Integer) entryAdd[1] + 1;
			entries.set(i, entryAdd);
		} else {
			// get index of entry.
			long day = calendar.getTimeInMillis();
			long dayFirst = ((GregorianCalendar) calendars[0]).getTimeInMillis();
			Integer i = (int) ((day - dayFirst) / (3600 * 24 * 1000));

			// Increase
			Object entryAdd[] = entries.get(i);
			entryAdd[1] = (Integer) entryAdd[1] + 1;
			entries.set(i, entryAdd);
		}
	}

	/**
	 * This method filters and transforms data depending on applicationName
	 * 
	 */
	@Override
	public boolean handleAuditEntry(Long entryId, String applicationName, String user, long time, Map<String, Serializable> values) {
		if (applicationName.equals("UserLogin")) {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTimeInMillis(time);
			if (period.compareTo("day") != 0)
				cal.set(GregorianCalendar.HOUR_OF_DAY, 0);
			cal.set(GregorianCalendar.MINUTE, 0);
			cal.set(GregorianCalendar.SECOND, 0);
			cal.set(GregorianCalendar.MILLISECOND, 0);
			String userEntryDate = (String) values.get("/UserLogin/args/userName/value") + cal.getTime();
			if (!userLog.contains(userEntryDate)) {
				userLog.add(userEntryDate);
				addEntry(new Date((Long) time));
			}
		}

		else
			addEntry(new Date((Long) time));

		return true;
	}

	@Override
	public boolean handleAuditEntryError(Long entryId, String errorMsg, Throwable error) {
		throw new AlfrescoRuntimeException(errorMsg, error);
	}

	@Override
	public boolean valuesRequired() {
		return valuesRequired;
	}

	public List<Object[]> getEntries() {
		return entries;
	}
}
