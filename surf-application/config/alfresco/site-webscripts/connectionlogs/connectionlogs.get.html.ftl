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

${msg("div-title")}
<ul>
	<#if items?exists>
		<table class="connections">
			<tr>
				<th>${msg("login")}</th>
				<th>${msg("status")}</th>
				<th>${msg("date")}</th>
			</tr>
			<#list items?sort_by("date")?reverse as data>
			<tr>
				<td>${data.login}</td>
				<#if data.fail == "true">
					<td><img src="${url.context}/images/failed.gif" title="Fail" alt="fail"> <span class="connection-failed">${msg("fail")}</span></td>
				<#else>
					<td><img src="${url.context}/images/successful.gif" title="Success" alt="success"> <span class="connection-succeed">${msg("succeed")}</span></td>
				</#if>
				<td>${data.date?datetime?string(msg("mask-date"))}</td>
			</tr>
			</#list>
		</table>
	</#if>
</ul>	
