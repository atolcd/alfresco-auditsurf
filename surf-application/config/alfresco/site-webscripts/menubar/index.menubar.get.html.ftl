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

<div id="menubar">
	<ul class="yui-nav">
		<li class="menubar-separator"><a id="menu_canvas" href="#canvas" title="${msg("charts-msg")}">${msg("charts")}</a></li>
		<li class="menubar-separator"><a id="menu_dash" href="#dash" title="${msg("dash-msg")}">${msg("dash")}</a></li>
		<li class="menubar-separator"><a id="menu_monit" href="#monit" title="${msg("mon-msg")}">${msg("mon")}</a></li>		
		<li><a id="menu_exp" href="#exp" title="${msg("export-msg")}">${msg("export")}</a></li>
	</ul>
	<ul class="logout">
		<li><a href="${url.context}/page/dologout" title="${msg("logout-msg")}">${msg("logout")}</a></li>
	</ul>
</div>