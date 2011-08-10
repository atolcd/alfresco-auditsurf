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

import static java.lang.management.ManagementFactory.getPlatformMBeanServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.WebScriptException;

public class AbstractJMXInfoGet extends DeclarativeWebScript implements InitializingBean {

	public void afterPropertiesSet() throws Exception {
	}

	private static final Map<ObjectName, MBeanServer> knownMBeanServers = new HashMap<ObjectName, MBeanServer>();
	private static final Map<ObjectName, MBeanServer> knownMBeanServersWithQuery = new HashMap<ObjectName, MBeanServer>();

	protected MBeanServer getMBeanServer(final ObjectName objectName) {
		if (knownMBeanServersWithQuery.containsKey(objectName)) {
			return knownMBeanServersWithQuery.get(objectName);
		}

		// no cached instances, search high and low...
		MBeanServer mbeanServer = null;

		final List<MBeanServer> servers = MBeanServerFactory.findMBeanServer(null);
		for (int i = 0; mbeanServer == null && i < servers.size(); i++) {
			try {
				if (servers.get(i).getObjectInstance(objectName) != null) {
					mbeanServer = servers.get(i);
				}
			} catch (InstanceNotFoundException e) {
				// woops, not registered here...
				throw new WebScriptException("[DatabaseInfoGet] not registered here...");
			}
		}

		if (mbeanServer == null) {
			// oh well, most likely it is here then...
			mbeanServer = getPlatformMBeanServer();
		}
		knownMBeanServers.put(objectName, mbeanServer);
		return mbeanServer;
	}

	protected MBeanServer getMBeanServerWithQuery(ObjectName query) {
		if (knownMBeanServersWithQuery.containsKey(query)) {
			return knownMBeanServersWithQuery.get(query);
		}

		// no cached instances, search high and low...
		MBeanServer mbeanServer = null;

		final List<MBeanServer> servers = MBeanServerFactory.findMBeanServer(null);
		for (int i = 0; mbeanServer == null && i < servers.size(); i++) {
			if (servers.get(i).queryNames(query, null) != null) {
				mbeanServer = servers.get(i);
			}
		}

		if (mbeanServer == null) {
			// oh well, most likely it is here then...
			mbeanServer = getPlatformMBeanServer();
		}
		knownMBeanServersWithQuery.put(query, mbeanServer);
		return mbeanServer;
	}

}
