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

public class RuntimeInfoGet extends AbstractJMXInfoGet {

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {

		try {

			// Map that will be passed to the template
			Map<String, Object> model = new HashMap<String, Object>();

			ObjectName runtimeName = new ObjectName("Alfresco:Name=Runtime");
			MBeanServer mbs = getMBeanServer(runtimeName);

			model.put("FreeMemory", (Long) mbs.getAttribute(runtimeName, "FreeMemory") / 1024 / 1024);
			model.put("MaxMemory", (Long) mbs.getAttribute(runtimeName, "MaxMemory") / 1024 / 1024);
			model.put("TotalMemory", (Long) mbs.getAttribute(runtimeName, "TotalMemory") / 1024 / 1024);

			return model;
		} catch (Exception e) {
			throw new WebScriptException("[RuntimeInfosGet] Error in executeImpl function : \n" + e.getMessage());
		}

	}
}
