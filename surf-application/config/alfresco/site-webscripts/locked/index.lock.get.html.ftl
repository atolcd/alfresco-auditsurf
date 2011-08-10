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

<#import "../refresh_script.html.ftl" as refresh>
<div id="Rec7" class="info">
	<div id="Ctrl_lock" class="titremontre hd" ><img src="${url.context}/images/locked.gif" alt=""><span class="titlebox">${msg("locked")}</span></div>
	<@refresh.displayRefreshButton />
	<div onClick="Display(this)" id="bt_lock" class="button buttonUp"></div>
	<div id="lock" class="montre bd">
		<#if locked?exists>
			<#if locked?string?trim=="nodata" || locked?string?trim=="error" || locked?string?trim=="invalid">
				<div class="${locked?string?trim}">${msg("${locked?string?trim}")}</div>
			<#else>
				${locked}
			</#if>
		<#else>
			<div class="info-msg">
				<img src="${url.context}/images/search_icon.gif" alt="" /> 
				<#if instance.htmlId?exists>
					<a href="javascript:refreshDashletWithLoading('${url.service?replace("${url.serviceContext}", ".")}?display=true','${instance.htmlId}','lock');">${msg("search")}</a>
				<#elseif args.htmlId?exists>
					<a href="javascript:refreshDashletWithLoading('${url.service?replace("${url.serviceContext}", ".")}?display=true','${args.htmlId}','lock');">${msg("search")}</a>
				</#if>
				<br>
				<img src="${url.context}/images/warning.gif" alt="" /> <span class="info-time-msg">${msg("search-msg")}</span>
			</div>
		</#if>
	</div>
</div>