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

public class SystemPropertiesGet extends AbstractJMXInfoGet {

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {

		try {
			// Map that will be passed to the template
			Map<String, Object> model = new HashMap<String, Object>();

			// Copy server object in a local object "MBeanProxy"
			ObjectName systemName = new ObjectName("Alfresco:Name=SystemProperties");
			MBeanServer mbsc = getMBeanServer(systemName);

			model.put("AlfrescoHome", mbsc.getAttribute(systemName, "alfresco.jmx.dir"));
			model.put("CatalinaHome", mbsc.getAttribute(systemName, "catalina.home"));
			model.put("HibernateDialect", mbsc.getAttribute(systemName, "hibernate.dialect"));
			model.put("JavaClassPath", mbsc.getAttribute(systemName, "java.class.path"));
			model.put("JavaHome", mbsc.getAttribute(systemName, "java.home"));
			model.put("JavaRuntimeName", mbsc.getAttribute(systemName, "java.runtime.name"));
			model.put("JavaRuntimeVersion", mbsc.getAttribute(systemName, "java.runtime.version"));
			model.put("JavaVersion", mbsc.getAttribute(systemName, "java.version"));
			model.put("OsArch", mbsc.getAttribute(systemName, "os.arch"));
			model.put("OsName", mbsc.getAttribute(systemName, "os.name"));
			model.put("OsVersion", mbsc.getAttribute(systemName, "os.version"));
			model.put("UserCountry", mbsc.getAttribute(systemName, "user.country"));
			model.put("UserLanguage", mbsc.getAttribute(systemName, "user.language"));
			model.put("UserTimezone", mbsc.getAttribute(systemName, "user.timezone"));

			return model;
		} catch (Exception e) {
			throw new WebScriptException("[SystemPropertiesGet] Error in executeImpl function");
		}
	}
}