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

<#setting locale=msg("locale")>
<#if node?exists>
<#escape x as jsonUtils.encodeJSONString(x)>
{
	"icon": "<#if node.icon64?exists>${url.context}${node.icon64}<#else>${url.context}images\filetypes64\_default.png</#if>",
	"name": {
		"id" : "name" , "value" : "${node.name}"
	},
	
	"type": {
		"id" : "type" , 
		"value" : 
			<#if node.parent.name=="wiki" && node.parent.hasAspect("st:siteContainer") > 
				"wiki"
			<#elseif node.parent.name == "blog" && node.parent.hasAspect("st:siteContainer")>
				"blog"
			<#else> 	
				"cm_content"
			</#if>		
	},	

	"properties":
	[		
		<#if node.properties.title?exists && node.properties.title?has_content>{"id" : "title" , "value" : "${node.properties.title}"},</#if>
	
		<#if !(node.parent.hasAspect("st:siteContainer") && (node.parent.name=="wiki" || node.parent.name=="blog")) > 
			<#if site.title?exists>{"id" : "site" , "value" : "${site.title} (${site.shortName})"},</#if>
		
			{ "id" : "path" , "value" : 
				<#if site.title?exists>
					<#if node.displayPath?split("documentLibrary")?last?has_content>
						"${node.displayPath?split("documentLibrary")?last}/"
					<#else>
						"/"
					</#if>
				<#else>
					<#if node.displayPath?has_content>
						"${node.displayPath}/"
					<#else>
						"/"
					</#if>
				</#if>
			},	
		</#if>
		
		<#if node.properties.description?exists && node.properties.description?has_content>{"id" : "description" , "value" : "${node.properties.description}"},</#if>

		<#if node.properties.author?exists &&  node.properties.author?has_content>{"id" : "author" , "value" : "${node.properties.author}"},</#if>

		<#if node.isDocument && node.properties.content.size?exists>{"id" : "size" , "value" : "${node.properties.content.size?string("0.##")}"},</#if>	
		
		<#if node.properties.creator?exists &&  node.properties.creator?has_content>{"id" : "creator" , "value" : "${node.properties.creator}"},</#if>

		{"id" : "created" , "value" : "${node.properties.created?datetime?string.medium}"},
		
		<#if node.properties.modifier?exists &&  node.properties.modifier?has_content>{"id" : "modifier" , "value" : "${node.properties.modifier}"},</#if>
		
		{"id" : "modified" , "value" : "${node.properties.modified?datetime?string.medium}"}		
	]
}
</#escape>
</#if>

