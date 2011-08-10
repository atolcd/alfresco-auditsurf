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
		<#if Edition?exists>
			"edition" :  "${Edition?js_string}" 
		<#else>
			"edition" :  "" 
		</#if>,
		<#if Name?exists>
			"name" : "${Name?js_string}"
		<#else>
			"name" : ""
		</#if>,
		<#if Schema?exists>
			"schema" : "${Schema?js_string}"
		<#else>
			"schema" : ""
		</#if>,
		<#if Version?exists>
			"version" : "${Version?js_string}"
		<#else>
			"version" : ""
		</#if>,
		<#if Days?exists>
			"days" : "${Days?js_string}"
		<#else>
			"days" : ""
		</#if>,
		<#if HeartBeat?exists>
			"heart" : "${HeartBeat?js_string}"
		<#else>
			"heart" : ""
		</#if>,
		<#if Holder?exists>
			"holder" : "${Holder?js_string}"
		<#else>
			"holder" : ""
		</#if>,
		<#if Issued?exists>
			"issued" : "${Issued?date?string.short}"
		<#else>
			"issued" : ""
		</#if>,
		<#if Issuer?exists>
			"issuer" : "${Issuer?js_string}"
		<#else>
			"issuer" : ""
		</#if>,
		<#if Remaining?exists>
			"remaining" : "${Remaining?js_string}"
		<#else>
			"remaining" : ""
		</#if>,
		<#if Subject?exists>
			"subject" : "${Subject?js_string}"
		<#else>
			"subject" : ""
		</#if>,
		<#if Valid?exists>
			"valid" : "${Valid?date?string.short}"
		<#else>
			"valid" : ""
		</#if>
}