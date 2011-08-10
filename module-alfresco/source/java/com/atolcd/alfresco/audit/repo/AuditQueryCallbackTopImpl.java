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
import java.util.Collections;
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

public class AuditQueryCallbackTopImpl implements AuditQueryCallback {
	private final boolean valuesRequired;
	private List<Object[]> entries;
	private Map<Serializable, Integer> countEntries;

	public AuditQueryCallbackTopImpl(boolean valuesRequired) {
		super();
		this.valuesRequired = valuesRequired;

		this.entries = new ArrayList<Object[]>();
		this.countEntries = new HashMap<Serializable, Integer>();
	}

	/**
	 * This method filters and transforms data depending on applicationName
	 * 
	 */
	@Override
	public boolean handleAuditEntry(Long entryId, String applicationName, String user, long time, Map<String, Serializable> values) {
		Serializable node = null;
		if (applicationName.equals("ReadFile")) {
			node = values.get("/ReadFile/args/nodeRef/value");
		} else if (applicationName.equals("ModifyFile")) {
			node = values.get("/ModifyFile/result/value");
		}

		if (countEntries.containsKey(node)) {
			countEntries.put(node, countEntries.get(node) + 1);
		} else {
			countEntries.put(node, 1);
		}

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

	public List<Object[]> getEntries(int MAX) {
		// Sort
		List<Integer> maxTop = new ArrayList<Integer>(countEntries.values());
		Collections.sort(maxTop, Collections.reverseOrder());

		if (MAX < maxTop.size())
			maxTop = maxTop.subList(0, MAX);

		// Search nodeRef
		for (Integer max : maxTop) {
			for (Serializable node : countEntries.keySet()) {
				if (countEntries.get(node) == max) {
					Object[] topFile = new Object[2];
					topFile[0] = node;
					topFile[1] = countEntries.get(node);

					entries.add(topFile);
					countEntries.remove(node);
					break;
				}
			}
		}

		return entries;
	}
}
