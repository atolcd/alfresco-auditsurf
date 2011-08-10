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

<#macro unconfigured>

<div style="border-top: 1px #999999 solid; border-left: 1px #999999 solid; border-right: 1px #000000 solid; border-bottom: 1px #000000 solid; padding: 8px; margin: 2px; background-color: #eeeeee; color: #000000" align="center" valign="middle">

<#if instance.properties["url"]?exists>
	${instance.properties["url"]}
<#else>
	${instance.properties["component-type-id"]}
</#if>
<br/>
<br/>
This component needs to be configured.

</div>

</#macro>
