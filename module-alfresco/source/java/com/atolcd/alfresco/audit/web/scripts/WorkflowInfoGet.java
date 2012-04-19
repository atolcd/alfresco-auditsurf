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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.workflow.WorkflowModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.PersonService;
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

public class WorkflowInfoGet extends DeclarativeWebScript implements InitializingBean {

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

	/**
	 * PRINCIPAL METHOD
	 * 
	 */
	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			Map<String, String[]> res = new HashMap<String, String[]>();

			String workflowId = req.getParameter("workflowId");

			if (workflowId != null && !workflowId.equals("")) {
				WorkflowInstance wi = this.workflowService.getWorkflowById(workflowId);

				if (wi.active) {
					// Description
					putEntry("description", wi.description, "string", res);

					// Type
					putEntry("type", wi.definition.getTitle(), "string", res);

					// start Date
					putEntry("createdOn", new java.sql.Timestamp(wi.startDate.getTime()), "date", res);

					// Initiator
					String username = "";
					if (this.nodeService.exists(wi.initiator)) {
						username = (String) this.nodeService.getProperty(wi.initiator, ContentModel.PROP_USERNAME);
						if (username != null) {
							NodeRef person = this.personService.getPerson(username);
							if (this.nodeService.exists(person))// If the node exists
								putEntry("initiator", (String) this.nodeService.getProperty(person, ContentModel.PROP_FIRSTNAME) + " "
										+ (String) this.nodeService.getProperty(person, ContentModel.PROP_LASTNAME), "string", res);
						}
					}

					/*** Get properties of the workflow ***/
					Map<QName, Serializable> properties = getProcessProperties(workflowId);
					if (properties != null) {
						// Due Date
						putEntry("dueDate", properties.get(WorkflowModel.PROP_DUE_DATE), "date", res);

						// Priority
						putEntry("priority", String.valueOf(properties.get(WorkflowModel.PROP_PRIORITY)), "string", res);

						// Status (Alfresco : Not Yet started, In Progress, On
						// Hold, Cancelled, Completed)
						putEntry("status", (String) properties.get(WorkflowModel.PROP_STATUS), "string", res);

						// Owner
						String owner = (String) properties.get(ContentModel.PROP_OWNER);
						if (owner != null) {
							NodeRef actorRef = personService.getPerson(owner);

							if (this.nodeService.exists(actorRef)) {
								StringBuilder sb = new StringBuilder();
								sb.append(nodeService.getProperty(actorRef, ContentModel.PROP_USERNAME)).append(" (")
										.append(nodeService.getProperty(actorRef, ContentModel.PROP_FIRSTNAME)).append(" ")
										.append(nodeService.getProperty(actorRef, ContentModel.PROP_LASTNAME)).append(")");

								putEntry("handler", sb.toString(), "string", res);
							}
						}
					}
				}
			}

			// Insert into the map
			model.put("workflowInfo", res.entrySet());

			return model;
		} catch (Exception e) {
			throw new WebScriptException("[WorkflowInfoGet] Error in execute function");
		}
	}

	/**
	 * 
	 * @param id
	 * @param value
	 * @param type
	 * @param res
	 */
	private void putEntry(String id, Object value, String type, Map<String, String[]> res) {
		if (value != null) {
			if (type.equals("date")) {
				String[] tab = { String.valueOf(((java.sql.Timestamp) value).getTime()), type };
				res.put(id, tab);
			} else {
				String[] tab = { value.toString(), type };
				res.put(id, tab);
			}
		}
	}

	/**
	 * 
	 * @param pathId
	 * @return
	 */
	private WorkflowTask getLastTask(String pathId) {
		WorkflowTask task = null;
		List<WorkflowTask> tasks = this.workflowService.getTasksForWorkflowPath(pathId);
		if (tasks.size() >= 1) {
			task = tasks.get(tasks.size() - 1);
		}
		return task;
	}

	/**
	 * 
	 * @param processId
	 * @return
	 */
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

	/**
	 * 
	 * @param processId
	 * @return
	 */
	public Map<QName, Serializable> getProcessProperties(String processId) {
		Map<QName, Serializable> res = null;
		List<WorkflowPath> paths = this.workflowService.getWorkflowPaths(processId);
		WorkflowTask task;
		if (paths.size() >= 1) {
			task = getLastTask(paths.get(0).id);
		} else {
			task = getFinishedProcessTask(processId);
		}

		if (task != null)
			res = task.properties;

		return res;
	}
}