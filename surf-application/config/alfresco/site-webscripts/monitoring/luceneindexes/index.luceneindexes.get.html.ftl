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

<#assign i = 0>
<div id="MonitRec5" class="info">
	<div id="Ctrl_luceneindexes" class="titremontre hd"><img src="${url.context}/images/luceneindexes.png" alt="lucene indexes info icon"><span class="titlebox">${msg("title")}</span></div>
	<div id="bt_luceneindexes" class="button buttonUp"></div>
	<div id="luceneindexes" class="montre bd">
		<#if errmessage?exists>
			<div class="${errmessage}">${msg("${errmessage?string}")}</div>
		<#else>
			<ul id="bloc_lucene">
			<#if data?exists>
				<#if data.luceneIndexes?exists>
					<#if data.luceneIndexes?size == 0>
						<div class="error">${msg("nodata")}</div>
					<#else>
						<#list data.luceneIndexes as luceneIndex>
						<#assign i = i + 1>
						<li class="lucene_li">
							<div id="lucene_${i}" class="title_lucene">${luceneIndex.title}<span class="bt_lucene buttonDown">&nbsp;</span></div>
							<div class="content_lucene cache">
								<ul>
									<li>${msg("actualSize")} : ${setUnit(luceneIndex.actualSize)}</li>
									<li>${msg("numberOfDocuments")} : ${luceneIndex.numberOfDocuments}</li>
									<li>${msg("numberOfFields")} : ${luceneIndex.numberOfFields}</li>
									<li>${msg("numberOfIndexedFields")} : ${luceneIndex.numberOfIndexedFields}</li>
									<li>${msg("usedSize")} : ${setUnit(luceneIndex.usedSize)}</li>
								</ul>
							</div>
						</li>
						</#list>
					</#if>
				<#else>
					<div class="error">${msg("error")}</div>
				</#if>					
			</#if>
			</ul>
		</#if>
	</div>
</div>

<#function setUnit param>
	<#if param?number == 0>
		<#return param+" "+msg("b-null")>
	<#elseif param?number gte 0 && param?number &lt; 1024>
		<#return param+" "+msg("b")>
	<#elseif param?number gte 1024 && param?number &lt; 1048576>
		<#assign res = param?number/1024 />
		<#return res?string("0.##")+" "+msg("kb")>
	<#else>
		<#assign res = param?number/1048576 />
		<#return res?string("0.##")+" "+msg("mb")>
	</#if>
</#function>