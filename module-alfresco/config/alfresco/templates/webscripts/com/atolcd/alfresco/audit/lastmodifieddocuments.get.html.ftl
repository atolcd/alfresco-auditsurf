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

<#setting locale=msg("locale")>
<#if LastModifiedDocs?exists>
	<#if LastModifiedDocs?size != 0>
		<ul>
		<#-- Display : icon + filename + date of creation + fileinfo's icon -->
		<#list LastModifiedDocs?sort_by("key")?reverse as data>
			<#assign node = companyhome.nodeByReference[data.value] />
			<li>
				<img src="${url.context}${node.icon16}" alt="">&nbsp; <#-- icon -->	
				<a href="${url.context}${node.downloadUrl}?ticket=${session.ticket}" title="Download this file">${node.properties.name}</a>&nbsp; <#-- filename -->
				(${data.key?datetime?string.medium?capitalize})<#-- Date -->
				<img src="${url.context}/images/icons/popup.gif" class="img_pointer" onclick="printInfo(this, '${node.nodeRef}')"> <#-- File info -->
			</li>				
		</#list>			
		</ul>
	<#else>
		nodata
	</#if>
<#else>
	error
</#if>