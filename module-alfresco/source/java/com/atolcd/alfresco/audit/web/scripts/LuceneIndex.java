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

public class LuceneIndex {
	private String title;
	private String actualSize;
	private String numberOfDocuments;
	private String numberOfFields;
	private String numberOfIndexedFields;
	private String usedSize;

	public LuceneIndex(String title, String actualSize, String numberOfDocuments, String numberOfFields, String numberOfIndexedFields,
			String usedSize) {
		this.title = title;
		this.actualSize = actualSize;
		this.numberOfDocuments = numberOfDocuments;
		this.numberOfFields = numberOfFields;
		this.numberOfIndexedFields = numberOfIndexedFields;
		this.usedSize = usedSize;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setActualSize(String actualSize) {
		this.actualSize = actualSize;
	}

	public String getActualSize() {
		return this.actualSize;
	}

	public void setNumberOfDocuments(String numberOfDocuments) {
		this.numberOfDocuments = numberOfDocuments;
	}

	public String getNumberOfDocuments() {
		return this.numberOfDocuments;
	}

	public void setNumberOfFields(String numberOfFields) {
		this.numberOfFields = numberOfFields;
	}

	public String getNumberOfFields() {
		return this.numberOfFields;
	}

	public void setNumberOfIndexedFields(String numberOfIndexedFields) {
		this.numberOfIndexedFields = numberOfIndexedFields;
	}

	public String getNumberOfIndexedFields() {
		return this.numberOfIndexedFields;
	}

	public void setUsedSize(String usedSize) {
		this.usedSize = usedSize;
	}

	public String getUsedSize() {
		return this.usedSize;
	}
}