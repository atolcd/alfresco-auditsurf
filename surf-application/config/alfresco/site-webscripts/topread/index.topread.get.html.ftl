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
<div id="Rec1" class="info">
	<div id="Ctrl_top_read" class="titremontre hd" ><img src="${url.context}/images/deployment_report.gif" alt="icon"><span class="titlebox">${msg("mostread")}</span></div>
	<@refresh.displayRefreshButton />
	<div onClick="Display(this)" id="bt_top_read" class="button buttonUp"></div>
	<div id="top_read" class="montre bd">
	<#if topread?string?trim=="nodata" || topread?string?trim=="error" || topread?string?trim=="invalid">
		<div class="${topread?string?trim}">${msg("${topread?string?trim}")}</div>
	<#else>
		${topread}
	</#if>
	</div>
</div>