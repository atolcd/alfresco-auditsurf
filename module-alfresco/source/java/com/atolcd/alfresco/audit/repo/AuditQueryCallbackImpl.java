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

package com.atolcd.alfresco.audit.repo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.service.cmr.audit.AuditService.AuditQueryCallback;

/***
 * 
 * @author
 * 
 */

public class AuditQueryCallbackImpl implements AuditQueryCallback {
	private final boolean valuesRequired;
	private List<Map<String, Object>> entries;

	public AuditQueryCallbackImpl(boolean valuesRequired) {
		super();
		this.valuesRequired = valuesRequired;
		this.entries = new ArrayList<Map<String, Object>>();
	}

	/**
	 * This method filters and transforms data depending on applicationName
	 * 
	 */
	@Override
	public boolean handleAuditEntry(Long entryId, String applicationName, String user, long time, Map<String, Serializable> values) {
		Map<String, Object> entry = new HashMap<String, Object>();
		entry.put("date", new Date((Long) time));
		entry.put("user", user);
		entry.put("applicationName", applicationName);

		if (applicationName.equals("UserLogin")) {
			entry.put("value", values.get("/UserLogin/args/userName/value"));
		} else if (applicationName.equals("CreateFile")) {
			entry.put("value", values.get("/CreateFile/result/value"));
		} else if (applicationName.equals("ReadFile")) {
			entry.put("value", values.get("/ReadFile/args/nodeRef/value"));
		} else if (applicationName.equals("ModifyFile")) {
			entry.put("value", values.get("/ModifyFile/result/value"));
		} else if (applicationName.equals("CreateUser")) {
			entry.put("value", values.get("/CreateUser/args/properties/value"));
		} else if (applicationName.equals("CreateWorkflow")) {
			entry.put("value", values.get("/CreateWorkflow/args/parameters/value"));
		}

		entries.add(entry);

		return true;
	}

	@Override
	public boolean handleAuditEntryError(Long entryId, String errorMsg, Throwable error) {
		throw new AlfrescoRuntimeException(errorMsg, error);
	}

	@Override
	public boolean valuesRequired() {
		return valuesRequired;
	}

	public List<Map<String, Object>> getEntries() {
		return entries;
	}
}
