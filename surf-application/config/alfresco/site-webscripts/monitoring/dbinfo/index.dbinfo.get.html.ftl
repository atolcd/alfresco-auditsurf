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

<div id="MonitRec6" class="info">
	<div id="Ctrl_dbinfo" class="titremontre hd" ><img src="${url.context}/images/dbinfo.png" alt="database info icon"><span class="titlebox">${msg("title")}</span></div>
	<div id="bt_dbinfo" class="button buttonUp"></div>
	<div id="dbinfo" class="montre bd">
		<#if errmessage?exists>
			<div class="${errmessage}">${msg("${errmessage?string}")}</div>
		<#else>
			<#if !db_name?exists && !db_version?exists && !dr_name?exists && !dr_version?exists && !db_url?exists && !db_username?exists>
				<div class="error">${msg("nodata")}</div>
			<#else>
				<ul>
					<#if db_name?exists>
						<li><span class="monitor_name">${msg("name")} :</span> ${db_name} </li>
					</#if>
					<#if db_version?exists>
						<li><span class="monitor_name">${msg("version")} :</span> ${db_version} </li>
					</#if>
					<#if dr_name?exists>
						<li><span class="monitor_name">${msg("dr_name")} :</span> ${dr_name} </li>
					</#if>
					<#if dr_version?exists>
						<li><span class="monitor_name">${msg("dr_version")} :</span> ${dr_version} </li>
					</#if>
					<#if db_url?exists>
						<li><span class="monitor_name">${msg("url")} :</span> ${db_url} </li>
					</#if>
					<#if db_username?exists>
						<li><span class="monitor_name">${msg("usr")} :</span> ${db_username} </li>
					</#if>						
				</ul>
			</#if>
		</#if>
	</div>
</div>