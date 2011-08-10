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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.util.Assert;

import com.atolcd.alfresco.audit.cmr.AuditQueriesService;

public class LastCreatedDocumentsGet extends DeclarativeWebScript implements InitializingBean {

	private AuditQueriesService auditQueriesService;
	private NodeService nodeService;

	// Setters
	public void setAuditQueriesService(AuditQueriesService auditQueriesService) {
		this.auditQueriesService = auditQueriesService;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(auditQueriesService);
		Assert.notNull(nodeService);
	}

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		try {
			// Map that will be passed to the template
			Map<String, Object> model = new HashMap<String, Object>();

			List<Map<String, Object>> entries = auditQueriesService.lastDocs();
			List<NodeRef> lastup = new ArrayList<NodeRef>();

			for (Map<String, Object> entry : entries)
				lastup.add((NodeRef) entry.get("value"));

			// Insert into the map
			model.put("LastCreatedDocs", lastup);

			return model;
		} catch (Exception e) {
			throw new WebScriptException("[LastCreatedDocumentsGet] Error in executeImpl function");
		}
	}
}