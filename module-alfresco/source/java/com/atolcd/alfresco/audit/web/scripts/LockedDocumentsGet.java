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

import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.DynamicNamespacePrefixResolver;
import org.alfresco.service.namespace.NamespaceService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.util.Assert;

public class LockedDocumentsGet extends DeclarativeWebScript implements InitializingBean {
	private NodeService nodeService;
	private SearchService searchService;
	private FileFolderService fileFolderService;

	// Setters
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public void setFileFolderService(FileFolderService fileFolderService) {
		this.fileFolderService = fileFolderService;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(nodeService);
		Assert.notNull(searchService);
		Assert.notNull(fileFolderService);
	}

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		try {
			// Map that will be passed to the template
			Map<String, Object> model = new HashMap<String, Object>();

			List<NodeRef> lockedDocsList = new ArrayList<NodeRef>();

			DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(null);
			namespacePrefixResolver.registerNamespace(NamespaceService.CONTENT_MODEL_PREFIX, NamespaceService.CONTENT_MODEL_1_0_URI);
			StoreRef storeRef = new StoreRef("workspace://SpacesStore");
			NodeRef rootNodeRef = this.nodeService.getRootNode(storeRef);
			List<NodeRef> lockedFileList = searchService.selectNodes(rootNodeRef, "	//.[@cm:lockType='READ_ONLY_LOCK']", null,
					namespacePrefixResolver, false);
			for (int i = 0; i < lockedFileList.size(); i++) {
				if (!this.fileFolderService.getFileInfo(lockedFileList.get(i)).isFolder()) {
					lockedDocsList.add(lockedFileList.get(i));
				}
			}

			// Insert into the map
			model.put("LockedDocs", lockedDocsList);

			return model;
		} catch (Exception e) {
			throw new WebScriptException("[LockedDocumentsGet] Error in executeImpl function");
		}
	}
}