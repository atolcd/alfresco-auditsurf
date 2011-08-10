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

<div id="MonitRec2" class="info">
	<div id="Ctrl_runtime" class="titremontre hd" ><img src="${url.context}/images/runtime.png" alt="runtime info icon"><span class="titlebox">${msg("title")}</span></div>
	<div id="bt_runtime" class="button buttonUp"></div>
	<div id="runtime" class="montre bd">
		<#if errmessage?exists>
			<div class="${errmessage}">${msg("${errmessage?string}")}</div>
		<#else>
			<#if !free?exists>
				<div class="error">${msg("nodata")}</div>
			<#else>
				<ul>
					<li><span class="monitor_name">${msg("free")} :</span> ${free} ${msg("mega")}</li>
					<li><span class="monitor_name">${msg("max")} : </span>${max} ${msg("mega")}</li>
					<li><span class="monitor_name">${msg("tot")} : </span>${total} ${msg("mega")}</li>
				</ul>
			</#if>
		</#if>
	</div>
</div>