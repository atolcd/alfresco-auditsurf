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

public class FileServerInfoGet extends AbstractJMXInfoGet {

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {

		try {

			ObjectName fscName = new ObjectName("Alfresco:Name=FileServerConfig");
			MBeanServer mbs = getMBeanServer(fscName);

			// Map that will be passed to the template
			Map<String, Object> model = new HashMap<String, Object>();

			model.put("cifs_enabled", mbs.getAttribute(fscName, "CIFSServerEnabled"));
			model.put("cifs_name", mbs.getAttribute(fscName, "CIFSServerName"));
			model.put("cifs_address", mbs.getAttribute(fscName, "CIFSServerAddress"));
			model.put("ftp_enabled", mbs.getAttribute(fscName, "FTPServerEnabled"));
			model.put("nfs_enabled", mbs.getAttribute(fscName, "NFSServerEnabled"));

			return model;
		} catch (Exception e) {
			throw new WebScriptException("[FileServerInfoGet] Error in executeImpl function");
		}
	}
}