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
		"text":"\n${msg("error")} ${code} : ${msg("webscript.code.${code}.name")}\n${msg("webscript.code.${code}.description")}",
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
	
	<#if value?size == 24>		
		<#assign mask = msg("mask.hour")/>
		<#if big>
			<#assign title = "| " + date?first?string(msg("mask.day.full"))?capitalize/>
		<#else>
			<#assign x_step = 3/>
		</#if>
	<#elseif value?size == 7>
		<#assign period = "week"/>
		<#if big>
			<#assign mask = msg("mask.day.complete")/>
			<#assign title = "| " + msg("week") + " " + date[1]?string("w")?capitalize + " (" + date[1]?string("yyyy") +")"/>
		<#else>
			<#assign mask = msg("mask.day")/>
			<#assign x_step = 2/>
		</#if>
	<#elseif value?size == 12>
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
		
		"title":
		{
			"text":"${msg("title")}  ${title}"
		},
		"bg_colour":"#FFFFFF",
		
		
		"elements":
		[
			{
				"colour":"#5993ED",
				"values" : [
					<#assign i =0 />
					<#list value as conn>
						<#if conn?int &gt; max_val>
							<#assign max_val = conn>
						</#if>
						
						{
							"top":${conn?c},					
							"tip":"${msg("title")} [#val#]\n
							<#if period == "week" || period == "month">${date[i]?date?string.medium?capitalize}"<#else>#x_label#"</#if>
						}
						<#if conn_has_next>,</#if>		

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
				"labels":		
				[
					<#list date as d>
						"${d?string(mask)?capitalize}"<#if d_has_next>,</#if> 
					</#list>		
				],
				"steps":${x_step}
			},
			"steps":${x_step},
			"grid_colour":"#B7B9BF"
		},
		<#assign max_val = max_val + 2/>
		"y_axis":
		{
			"colour":"#B7B9BF",
			"max":${max_val?c}, 
			"steps":${((max_val / 10)?round)?c},
			"grid_colour":"#B7B9BF"
		},
		"menu":
		{
			"colour":"#E0E0ff",
			"outline_colour":"#707070",
			"values":
			[

				{"type":"camera-icon","text":"Make it Big","javascript-function":"maximize"}
			]
		}
	}
	</@compress>
</#if>