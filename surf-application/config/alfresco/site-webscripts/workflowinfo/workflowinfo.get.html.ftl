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

<#if items?exists>
<div class="panelinfo_content">
	<div class="panelinfo_td_img">
		<img src="${url.context}/images/workflow_logo.png" alt="Workflow icon">
	</div>
	<div class="panelinfo_td_data">
		<ul>
		<#if items?size &gt; 0>
			<#list items?reverse as data>
				<#if data.type == "date">
					<li><span class="panelinfo_name">${msg("${data.id}")?cap_first}</span><span class="panelinfo_value"> ${data.value?datetime?string(msg("mask-${data.id}"))}</span></li>
				<#else>
					<#if data.value != "">
						<#if data.id == "description">
							<li><span class="panelinfo_name">${msg("${data.id}")?cap_first}</span><span class="panelinfo_desc"> ${data.value?cap_first}</span></li>
						<#elseif data.id == "status">
							<#assign statusname = data.value?lower_case?replace(" ","-") />
							<li><span class="panelinfo_name">${msg("${data.id}")?cap_first}</span><span class="panelinfo_value"> ${msg("status-${statusname}")?cap_first}</span></li>
						<#elseif data.id == "priority">
							<li><span class="panelinfo_name">${msg("${data.id}")?cap_first}</span><span class="panelinfo_value"> ${msg("priority-${data.value}")}</span></li>
						<#else>
							<li><span class="panelinfo_name">${msg("${data.id}")?cap_first}</span><span class="panelinfo_value"> ${data.value?cap_first?replace(":", "")}</span></li>
						</#if>
					</#if>
				</#if>
			</#list>
		<#else>
			<div class="invalid">
				<br>${msg("error_refresh")}<br>
			</div>
		</#if>
		</ul>
	</div>
</div>
<#else>
	<#if errmessage?exists>
		<div class="panelinfo_content">
			<div class="${errmessage}">${msg("${errmessage?string}")}</div>
		</div>

		<#if code?exists && code == "401">
			<br>
			<div class="reconnect">
				<img src="${url.context}/images/login.gif" alt="" /> <a href="${url.context}/page/dologout">${msg("reconnect")}</a>
			</div>
		</#if>
	<#else>
		<div id="panelInfoWorkflow">
			<div class="hd">${msg("div-title")}</div>
			<div id="workflowinfo"></div>
		</div>
	</#if>
</#if>

