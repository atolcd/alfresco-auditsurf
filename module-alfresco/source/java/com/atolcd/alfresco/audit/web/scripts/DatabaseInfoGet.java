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

import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.atolcd.alfresco.audit.cmr.AbstractJMXInfoGet;

public class DatabaseInfoGet extends AbstractJMXInfoGet {

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {

		try {
			// Map that will be passed to the template
			Map<String, Object> model = new HashMap<String, Object>();

			// Copy server object in a local object "MBeanProxy"
			ObjectName dbName = new ObjectName("Alfresco:Name=DatabaseInformation");
			MBeanServer mbs = getMBeanServer(dbName);

			model.put("DatabaseProductName", mbs.getAttribute(dbName, "DatabaseProductName"));
			model.put("DatabaseProductVersion", mbs.getAttribute(dbName, "DatabaseProductVersion"));
			model.put("DriverName", mbs.getAttribute(dbName, "DriverName"));
			model.put("DriverVersion", mbs.getAttribute(dbName, "DriverVersion"));
			model.put("URL", mbs.getAttribute(dbName, "URL"));
			model.put("UserName", mbs.getAttribute(dbName, "UserName"));

			return model;
		} catch (Exception e) {
			throw new WebScriptException("[DatabaseInfoGet] Error in executeImpl function : \n" + e.getMessage());
		}

	}
}