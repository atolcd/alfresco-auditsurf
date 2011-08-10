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
<div id="Rec6" class="info">
	<div id="Ctrl_last_usr" class="titremontre hd" ><img src="${url.context}/images/add_user.gif" alt=""><span class="titlebox">${msg("lastusers")}</span></div>
	<@refresh.displayRefreshButton />
	<div onClick="Display(this)" id="bt_last_usr" class="button buttonUp"></div>
	<div id="last_usr" class="montre bd">
		<#if lastuser?string?trim=="nodata" || lastuser?string?trim=="error" || lastuser?string?trim=="invalid">
			<div class="${lastuser?string?trim}">${msg("${lastuser?string?trim}")}</div>
		<#else>
			${lastuser}
		</#if>
	</div>
</div>