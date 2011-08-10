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

public class ContentStore {
	private String name;
	private String writeSupported;
	private String totalSize;

	public ContentStore(String name, String writeSupported, String totalSize) {
		this.name = name;
		this.writeSupported = writeSupported;
		this.totalSize = totalSize;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setWriteSupported(String writeSupported) {
		this.writeSupported = writeSupported;
	}

	public String getWriteSupported() {
		return this.writeSupported;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	public String getTotalSize() {
		return this.totalSize;
	}

}
