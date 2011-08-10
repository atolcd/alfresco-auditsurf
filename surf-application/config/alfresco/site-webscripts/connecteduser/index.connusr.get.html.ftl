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
<div id="Rec3" class="info">
	<div id="Ctrl_conn_user" class="titremontre hd" ><img src="${url.context}/images/users.gif" alt="icon"><span class="titlebox">${msg("connecteduser")}</span></div>
	<@refresh.displayRefreshButton />
	<div onClick="Display(this)" id="bt_conn_user" class="button buttonUp"></div>
	<div id="conn_user" class="montre bd">
	<#if connecteduser?string?trim=="nodata" || connecteduser?string?trim=="error" || connecteduser?string?trim=="invalid">
		<div class="${connecteduser?string?trim}">${msg("${connecteduser?string?trim}")}</div>
	<#else>
		${connecteduser}
	</#if>
	</div>
</div>