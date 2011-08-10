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

{
		<#if DatabaseProductName?exists>
			"Database Product Name" : "${DatabaseProductName?js_string}"
		<#else>
			"Database Product Name" : ""
		</#if>,
		<#if DatabaseProductVersion?exists>
			"Database Product Version" : "${DatabaseProductVersion?js_string}"	
		<#else>
			"Database Product Version" : ""	
		</#if>,
		<#if DriverName?exists>
			"Driver Name" : "${DriverName?js_string}"		
		<#else>
			"Driver Name" : ""		
		</#if>,
		<#if DriverVersion?exists>
			"Driver Version" : "${DriverVersion?js_string}"		
		<#else>
			"Driver Version" : ""		
		</#if>,
		<#if URL?exists>
			"URL" : "${URL?js_string}"		
		<#else>
			"URL" : ""		
		</#if>,
		<#if UserName?exists>
			"User Name" : "${UserName?js_string}"		
		<#else>
			"User Name" : ""		
		</#if>	
}