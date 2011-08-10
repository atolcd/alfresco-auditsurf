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
<div id="Rec4" class="info">
	<div id="Ctrl_last_mod" class="titremontre hd" ><img src="${url.context}/images/Change_details.gif" alt="icon"><span class="titlebox">${msg("lastmodified")}</span></div>
	<@refresh.displayRefreshButton />
	<div onClick="Display(this)" id="bt_last_mod" class="button buttonUp"></div>
	<div id="last_mod" class="montre bd">
		<#if lastmod?string?trim=="nodata" || lastmod?string?trim=="error" || lastmod?string?trim=="invalid">
			<div class="${lastmod?string?trim}">${msg("${lastmod?string?trim}")}</div>
		<#else>
			${lastmod}
		</#if>
	</div>
</div>