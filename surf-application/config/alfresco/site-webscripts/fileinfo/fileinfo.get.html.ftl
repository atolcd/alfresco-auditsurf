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

<#if data?exists>
<div class="panelinfo_content">
	<#if data?has_content>
		<div class="panelinfo_td_img">
			<img src="${data.icon}" alt="icon" title="icon">
		</div>
		<div class="panelinfo_td_data">
			<ul>
				<li>
					<span class="panelinfo_name">${msg("${data.name.id}")}</span>
					<span class="panelinfo_value"> ${data.name.value}</span>
				</li>

				<li>
					<span class="panelinfo_name">${msg("${data.type.id}")}</span>
					<span class="panelinfo_value">${msg("${data.type.value}")}</span>
				</li>

				<#if data.properties?size &gt; 0>
					<#list data.properties as property>
							<li>
								<span class="panelinfo_name">${msg("${property.id}")}</span>
								<#if property.id == "size">
									<span class="panelinfo_value"> ${setUnit(property.value)}</span>
								<#else>
									<span class="panelinfo_value"> ${property.value}</span>
								</#if>
							</li>
					</#list>
				</#if>
			</ul>
		</div>
	<#else>
		<div class="invalid">
			${msg("error_refresh")}<br>
		</div>
	</#if>
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
		<div id="panelInfo">
			<div class="hd">${msg("div-title")}</div>
			<div id="fileinfo">
			</div>
		</div>
	</#if>
</#if>

<#function setUnit param>
	<#if param?number == 0>
		<#return param+" "+msg("b-null")>
	<#elseif param?number gte 0 && param?number &lt; 1024>
		<#return param+" "+msg("b")>
	<#elseif param?number gte 1024 && param?number &lt; 1048576>
		<#assign res = param?number/1024 />
		<#return res?string("0.##")+" "+msg("kb")>
	<#else>
		<#assign res = param?number/1048576 />
		<#return res?string("0.##")+" "+msg("mb")>
	</#if>
</#function>