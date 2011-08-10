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

<div id="MonitRec1" class="info">
	<div id="Ctrl_alf_info" class="titremontre hd" ><img src="${url.context}/images/alfinfo.png" alt="alfresco info icon"><span class="titlebox">${msg("title")}</span></div>
	<div id="bt_alf_info" class="button buttonUp"></div>
	<div id="alf_info" class="montre bd">
		<#if errmessage?exists>
			<div class="${errmessage}">${msg("${errmessage?string}")}</div>
		<#else>	
			<ul>
				<#if subject !="">
					<li><span class="monitor_name">${msg("Subject")} :</span> ${subject} </li>
				</#if>
				<#if name !="">
					<li><span class="monitor_name">${msg("Name")} :</span> ${name} </li>
				</#if>
				<#if issued !="">
					<li><span class="monitor_name">${msg("Issued")} :</span> ${issued} </li>	
				</#if>
				<#if valid !="">
					<li><span class="monitor_name">${msg("ValidUntil")} :</span> ${valid} </li>			
				</#if>
				<#if schema !="" && schema !="null">
					<li><span class="monitor_name">${msg("Schema")} :</span> ${schema} </li>
				</#if>
				<#if version !="">
					<li><span class="monitor_name">${msg("Version")} :</span> ${version} </li>
				</#if>
				<#if days !="" && days!= "null">
					<li><span class="monitor_name">${msg("Days")} :</span> ${days} ${msg("d")}</li>
				</#if>
				<#if heart=="true">
					<li><span class="monitor_name">${msg("HeartbeatDisabled")} :</span>${heart}</li>
				</#if>
				<#if holder!="">
					<li><span class="monitor_name">${msg("Holder")} :</span> ${holder} </li>
				</#if>
				<#if remaining!="" && remaining!= "null">
					<li><span class="monitor_name">${msg("RemainingDays")} :</span> ${remaining} </li>	
				</#if>
			</ul>		
		</#if>
	</div>
</div>