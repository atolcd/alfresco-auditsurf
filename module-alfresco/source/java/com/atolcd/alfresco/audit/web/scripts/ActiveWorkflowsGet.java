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

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.workflow.WorkflowModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.cmr.workflow.WorkflowDefinition;
import org.alfresco.service.cmr.workflow.WorkflowInstance;
import org.alfresco.service.cmr.workflow.WorkflowPath;
import org.alfresco.service.cmr.workflow.WorkflowService;
import org.alfresco.service.cmr.workflow.WorkflowTask;
import org.alfresco.service.cmr.workflow.WorkflowTaskQuery;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.util.Assert;

public class ActiveWorkflowsGet extends DeclarativeWebScript implements InitializingBean {

	private NodeService nodeService;
	private WorkflowService workflowService;
	private PersonService personService;
	private NamespaceService namespaceService;

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setWorkflowService(WorkflowService workflowService) {
		this.workflowService = workflowService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public void setNamespaceService(NamespaceService namespaceService) {
		this.namespaceService = namespaceService;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(workflowService);
		Assert.notNull(nodeService);
		Assert.notNull(personService);
		Assert.notNull(namespaceService);
	}

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		try {
			// Map that will be passed to the template
			Map<String, Object> model = new HashMap<String, Object>();
			Map<Date, String[]> workflowList = new HashMap<Date, String[]>();
			List<WorkflowDefinition> wd = this.workflowService.getAllDefinitions();

			for (WorkflowDefinition w : wd) {
				List<WorkflowInstance> lwi = this.workflowService.getActiveWorkflows(w.getId());
				if (lwi.size() > 0) {
					for (WorkflowInstance wi : lwi) {
						if (wi.active) {
							String firstName = "", lastName = "", s_status = "";

							// Get username, firstName and lastName
							String username = (String) this.nodeService.getProperty(wi.initiator, ContentModel.PROP_USERNAME);
							if (username != null) {
								if (personService.personExists(username)) {
									NodeRef person = this.personService.getPerson(username);
									if (this.nodeService.exists(person)) {// If the node exists
										firstName = (String) this.nodeService.getProperty(person, ContentModel.PROP_FIRSTNAME);
										lastName = (String) this.nodeService.getProperty(person, ContentModel.PROP_LASTNAME);
									}
								}
							}
							// Status
							Map<QName, Serializable> properties = getProcessProperties(wi.id);
							if (properties != null)
								s_status = (String) properties.get(WorkflowModel.PROP_STATUS);

							// Process Type
							String processType = (String) wi.definition.getTitle();

							String[] wf_properties = { processType, firstName, lastName, username, s_status, wi.id };
							workflowList.put(wi.startDate, wf_properties);
						}
					}
				}
			}
			// Insert into the map
			model.put("ActiveWorkflow", workflowList.entrySet());

			return model;
		} catch (Exception e) {
			throw new WebScriptException("[ActiveWorkflowsGet] Error in executeImpl function " + e.getMessage());
		}
	}

	public Map<QName, Serializable> getProcessProperties(String processId) {
		Map<QName, Serializable> res = null;
		List<WorkflowPath> paths = this.workflowService.getWorkflowPaths(processId);
		WorkflowTask task = null;
		if (paths.size() >= 1) {
			task = getLastTask(paths.get(paths.size() - 1).id);
		} else {
			task = getFinishedProcessTask(processId);
		}

		if (task != null) {
			res = task.properties;
		}

		return res;
	}

	private WorkflowTask getLastTask(String pathId) {
		WorkflowTask task = null;
		List<WorkflowTask> tasks = this.workflowService.getTasksForWorkflowPath(pathId);
		if (tasks.size() >= 1) {
			task = tasks.get(tasks.size() - 1);
		}

		return task;
	}

	public WorkflowTask getFinishedProcessTask(String processId) {
		WorkflowTask task = null;
		WorkflowTaskQuery query = new WorkflowTaskQuery();
		query.setProcessId(processId);
		List<WorkflowTask> tasks = workflowService.queryTasks(query);

		if (!tasks.isEmpty()) {
			task = tasks.get(tasks.size() - 1);
		}

		return task;
	}
}