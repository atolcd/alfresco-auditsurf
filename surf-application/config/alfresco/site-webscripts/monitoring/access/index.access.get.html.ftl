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

<div id="MonitRec7" class="info">
	<div id="Ctrl_access" class="titremontre hd" ><img src="${url.context}/images/access.png" alt="access info icon"><span class="titlebox">${msg("title")}</span></div>
	<div id="bt_access" class="button buttonUp"></div>
	<div id="access" class="montre bd">
		<#if errmessage?exists>
			<div class="${errmessage}">${msg("${errmessage?string}")}</div>
		<#else>
			<ul>
				<li><span class="monitor_name">CIFS :</span> ${msg("${cifs}")}</li>
				<#if cifs=="true">					
					<li class="indented">${msg("name")} : ${cifs_name}</li>
					<#if cifs_adr!=" ">
						<li class="indented">${msg("address")} : ${cifs_adr}</li>
					</#if>
				</#if>
				<li><span class="monitor_name">FTP :</span> ${msg("${ftp}")}</li>
				<li><span class="monitor_name">NFS :</span> ${msg("${nfs}")}</li>
			</ul>		
		</#if>		
	</div>
</div>