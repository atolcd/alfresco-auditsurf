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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.alfresco.repo.security.authentication.AbstractAuthenticationService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.util.Assert;

public class ConnectedUsersGet extends DeclarativeWebScript implements InitializingBean {
	private AbstractAuthenticationService authenticationService;

	public void setRawAuthenticationService(AbstractAuthenticationService rawAuthenticationService) {
		this.authenticationService = rawAuthenticationService;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(authenticationService);
	}

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		try {
			// Map that will be passed to the template
			Map<String, Object> model = new HashMap<String, Object>();

			// list of users with tickets (non expired only)
			Set<String> usersConnectedSet = new HashSet<String>(this.authenticationService.getUsersWithTickets(true));

			// Insert into the map
			model.put("ConnectedUsers", usersConnectedSet);

			return model;
		} catch (Exception e) {
			throw new WebScriptException("[ConnectedUsersGet] Error in executeImpl function");
		}
	}
}