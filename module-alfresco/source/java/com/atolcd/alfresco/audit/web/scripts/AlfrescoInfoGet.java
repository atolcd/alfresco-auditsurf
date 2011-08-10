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

import org.alfresco.service.descriptor.Descriptor;
import org.alfresco.service.descriptor.DescriptorService;
import org.alfresco.service.license.LicenseDescriptor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.util.Assert;

public class AlfrescoInfoGet extends DeclarativeWebScript implements InitializingBean {

	private DescriptorService descriptorService;

	public void setDescriptorService(DescriptorService descriptorService) {
		this.descriptorService = descriptorService;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(descriptorService);
	}

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {

		try {
			// Map that will be passed to the template
			Map<String, Object> model = new HashMap<String, Object>();
			LicenseDescriptor ld = this.descriptorService.getLicenseDescriptor();
			Descriptor desc = this.descriptorService.getCurrentRepositoryDescriptor();

			model.put("Edition", desc.getEdition());
			model.put("Name", desc.getName());
			model.put("Schema", String.valueOf(desc.getSchema()));
			model.put("Version", desc.getVersion());
			model.put("Days", String.valueOf(ld.getDays()));
			model.put("HeartBeat", String.valueOf(ld.isHeartBeatDisabled()));
			model.put("Holder", ld.getHolder().getName());
			model.put("Issued", ld.getIssued());
			model.put("Issuer", ld.getIssuer().getName());
			model.put("Remaining", String.valueOf(ld.getRemainingDays()));
			model.put("Subject", ld.getSubject());
			model.put("Valid", ld.getValidUntil());
			return model;
		} catch (Exception e) {
			throw new WebScriptException("[AlfrescoInfoGet] Error in executeImpl function");
		}
	}
}
