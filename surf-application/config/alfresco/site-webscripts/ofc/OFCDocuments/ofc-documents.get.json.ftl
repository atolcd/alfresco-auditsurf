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

<#if error?exists>
{ 
	"title":
	{
		"text":"${msg("error")} ${code} : ${msg("webscript.code.${code}.name")}\n${msg("webscript.code.${code}.description")}",
		"style": "{font-size: 13px; font-family: Times New Roman; font-weight: bold; color: #FF0000; text-align: center;}"
	},
	"elements": [ ], 
	 "radar_axis": 
	 { 
		 "colour": "#ffffff", 
		 "grid-colour": "#ffffff"
	 },
	 "bg_colour": "#ffffff" 
 }
<#else>
	<#setting locale=msg("locale")>
	<#assign max_val = 8/>
	<#assign mask = ""/>
	<#assign x_step = 1/>
	<#assign title = ""/>
	<#assign period = ""/>

	<#if crea?size == 24>
		<#assign mask = msg("mask.hour")/>
		<#if big>
			<#assign title = "| " + date?first?string(msg("mask.day.full"))?capitalize/>
		<#else>
			<#assign x_step = 3/>
		</#if>
	<#elseif crea?size == 7>
		<#if big>
			<#assign mask = msg("mask.day.complete")/>
			<#assign title = "| " + msg("week") + " " + date[1]?string("w")?capitalize + " (" + date[1]?string("yyyy") +")"/>
		<#else>
			<#assign mask = msg("mask.day")/>
			<#assign x_step = 2/>
		</#if>
	<#elseif crea?size == 12>
		<#if big>
			<#assign mask = msg("mask.month.full")/>
			<#assign title = "| " + date?first?string("yyyy")/>
		<#else>
			<#assign mask = msg("mask.month")/>
			<#assign x_step = 2/>
		</#if>
	<#else>
		<#assign mask = msg("mask.day")/>
		<#assign period = "month"/>
		<#if big>
			<#assign x_step = 2/>
			<#assign title = "| " + date?first?string("MMMM yyyy")?capitalize/>
		<#else>
			<#assign x_step = 5/>
		</#if>
	</#if>	

	<@compress single_line=true>{
			"title":{
				"text":"Documents  ${title}"
			},

			"bg_colour":"#FFFFFF",

			"elements":[
				{
					"colour":"#34DA00",
					"text": "${msg("title.modification")}",
					"values":[
						<#assign i =0 />
						<#list mod as data>
							<#if data &gt; max_val>
								<#assign max_val = data/>						
							</#if>
							{
								"top":${data?c},
								"tip":"${msg("title.modification")} [#val#]\n
								<#if period == "month">${date[i]?date?string.medium?capitalize}"<#else>#x_label#"</#if>
							}
							<#if data_has_next>,</#if>
							
							<#assign i = i + 1 />
						</#list>
					],
					"type":"bar_glass"				
				},
				
				{
					"colour":"#5993ED",
					"text": "${msg("title.readings")}",
					"values":[
						<#assign i =0 />
						<#list read as data>
							<#if data &gt; max_val>
								<#assign max_val = data/>						
							</#if>
							{
								"top":${data?c},					
								"tip":"${msg("title.readings")} [#val#]\n
								<#if period == "month">${date[i]?date?string.medium?capitalize}"<#else>#x_label#"</#if>
							}
							<#if data_has_next>,</#if>
							
							<#assign i = i + 1 />
						</#list>
					],
					"type":"bar_glass"	
				},
				
				{
					"colour":"#F9A116",
					"text": "${msg("title.creation")}",
					"values":[
						<#assign i =0 />
						<#list crea as data>
							<#if data &gt; max_val>
								<#assign max_val = data/>						
							</#if>
							{
								"top":${data?c},					
								"tip":"${msg("title.creation")} [#val#]\n
								<#if period == "month">${date[i]?date?string.medium?capitalize}"<#else>#x_label#"</#if>
							}
							<#if data_has_next>,</#if>
							
							<#assign i = i + 1 />
						</#list>
					],
					"type":"bar_glass"	
				}			
			],

			"x_axis":
			{
				"colour":"#B7B9BF",
				"labels":
				{
					"colour":"#000000",
					"labels":[
					<#list date as d>
						"${d?string(mask)?capitalize}"<#if d_has_next>,</#if> 
					</#list>		
					],
					"steps":${x_step}
				},
				"steps":1,
				"grid_colour":"#B7B9BF"
			},
		<#assign y_step = (max_val / 10)?ceiling />
		<#assign max_val = max_val + 2/>		
			"y_axis":{
				"colour":"#B7B9BF",
				"max": ${max_val?c},
				"steps":${y_step?c},
				"grid_colour":"#B7B9BF"
			}
		}
	</@compress>
</#if>