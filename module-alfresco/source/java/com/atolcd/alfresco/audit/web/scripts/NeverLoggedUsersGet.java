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
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.PersonService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.util.Assert;

import com.atolcd.alfresco.audit.cmr.AuditQueriesService;

public class NeverLoggedUsersGet extends DeclarativeWebScript implements InitializingBean {
	private AuditQueriesService auditQueriesService;
	private PersonService personService;
	private NodeService nodeService;

	public void setAuditQueriesService(AuditQueriesService auditQueriesService) {
		this.auditQueriesService = auditQueriesService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(auditQueriesService);
		Assert.notNull(personService);
		Assert.notNull(nodeService);
	}

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		try {
			List<Map<String, Object>> peopleConnectOnce = auditQueriesService.usersNeverLog();

			Set<String> peopleAuthenticate = new HashSet<String>();

			for (Map<String, Object> entry : peopleConnectOnce)
				peopleAuthenticate.add((String) entry.get("value"));

			Set<NodeRef> allPeople = personService.getAllPeople();
			Set<String> allPeopleString = new HashSet<String>();
			for (NodeRef people : allPeople)
				allPeopleString.add((String) nodeService.getProperty(people, ContentModel.PROP_USERNAME));

			// Add guest user, authentication/authenticate don't generate audit
			// result for guest user.
			peopleAuthenticate.add(AuthenticationUtil.getGuestUserName());

			allPeopleString.removeAll(peopleAuthenticate);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("NeverLoggedUsers", allPeopleString);
			return model;
		} catch (Exception e) {
			throw new WebScriptException("[NeverLoggedUsersGet] Error in executeImpl function");
		}
	}
}