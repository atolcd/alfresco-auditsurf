<#--
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
02110-1301, USA. -->

{
	"cifs_enabled" :  "<#if cifs_enabled?exists>${cifs_enabled?string}</#if>",
	"cifs_address" : "<#if cifs_adress?exists>${cifs_address?string}<#else> </#if>",
	"cifs_name" : "<#if cifs_name?exists>${cifs_name?string}<#else> </#if>",
	"ftp" : "<#if ftp_enabled?exists>${ftp_enabled?string}</#if>",
	"nfs" : "<#if nfs_enabled?exists>${nfs_enabled?string}</#if>"
}