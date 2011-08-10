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

package com.atolcd.alfresco.audit.cmr;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AuditQueriesService {

	public Map<Date, Integer> countConnections(final Calendar[] calendars, String period);

	public Map<Date, Integer> countCreateUsers(final Calendar[] calendars, String period);

	public Map<Date, Integer> countCreateDocs(final Calendar[] calendars, String period);

	public Map<Date, Integer> countReadDocs(final Calendar[] calendars, String period);

	public Map<Date, Integer> countCheckins(final Calendar[] calendars, String period);

	public Map<Date, Integer> countWorkflows(final Calendar[] calendars, String period);

	public List<Object[]> topRead();

	public List<Object[]> topModif();

	public List<Map<String, Object>> lastDocs();

	public List<Map<String, Object>> lastModif();

	public List<Map<String, Object>> lastUsers();

	public List<Map<String, Object>> usersNeverLog();

	public List<Object[]> getConnectionLogs(String method, Calendar start, Calendar end, boolean noAdmin, int maxdisplay);
}
