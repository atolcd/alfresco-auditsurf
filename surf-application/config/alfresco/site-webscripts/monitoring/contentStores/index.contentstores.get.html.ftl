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

<div id="MonitRec3" class="info">
	<div id="Ctrl_contentStores" class="titremontre hd" ><img src="${url.context}/images/contentstores.png" alt="content stores icon"><span class="titlebox">${msg("title")}</span></div>
	<div id="bt_contentStores" class="button buttonUp"></div>
	<div id="contentStores" class="montre bd">
		<#if errmessage?exists>
			<div class="${errmessage}">${msg("${errmessage?string}")}</div>
		<#else>
			<#if data?exists>
				<#if data.contentStores?size == 0>
					<div class="error">${msg("nodata")}</div>
				<#else>
					<#list data.contentStores as contentStore>
						<#if contentStore_has_next>
						<ul class="bottom_bordered">
						<#else>
						<ul>
						</#if>
							<li><span class="monitor_name">${contentStore.name?replace("|",":")}</span></li>
							<li class="indented">${msg("wr")} : ${msg("${contentStore.writeSupported}")}</li>
							<li class="indented">${msg("tot")} : ${setUnit(contentStore.totalSize)}</li>
						</ul>
					</#list>
				</#if>
			</#if>
		</#if>
	</div>
</div>

<#function setUnit param>
	<#if param?number == 0>
		<#return param+" "+msg("b-null")>
	<#elseif param?number gte 0 && param?number &lt; 1024>
		<#return param+" "+msg("b")>
	<#elseif param?number gte 1024 && param?number &lt; 1048576>
		<#assign res = param?number/1024 />
		<#return res?string("0.##")+" "+msg("kb")>
	<#elseif param?number gte 1048576 && param?number &lt; 1073741824>
		<#assign res = param?number/1048576 />
		<#return res?string("0.##")+" "+msg("mb")>
	<#else>
		<#assign res = param?number/1073741824 />
		<#return res?string("0.##")+" "+msg("gb")>
	</#if>
</#function>