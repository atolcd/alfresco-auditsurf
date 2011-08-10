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

<#include "include/alfresco-template.ftl" />

<#if user.isAdmin>
		<@templateHeader>
			<link rel="stylesheet" href="${url.context}/css/alf.css" type="text/css" media="screen">
			<!--[if lte IE 7]> <link href="${url.context}/css/alf_ie.css" rel="stylesheet" type="text/css" media="screen"> <![endif]-->
			<!--[if IE 8]> <link href="${url.context}/css/alf_ie8.css" rel="stylesheet" type="text/css" media="screen"> <![endif]-->

			<@script type="text/javascript" src="${url.context}/js/core.js"></@script>
		</@>

		<@templateBody>
			<div id="yui-cms-loading">
				<div id="yui-cms-float"></div>
			</div>
			<@script type="text/javascript" src="${url.context}/js/loading.js"></@script>

			<div id="banner"><a href="${url.context}/page/home"><img src="${url.context}/images/bandeau.png" alt="AuditSurf" title="AuditSurf"></a></div>
			<div id="sponsor"><a href="http://www.atolcd.com" target="_blank"><img src="${url.context}/images/sponsored.png" alt="${msg('sponsor-by')}" title="${msg('sponsor-by')}"></a></div>

			<div id="global" class="yui-navset">
				<@region id="menubar" scope="page" protected=true />
				<div id="testticket"></div>
				<div class="yui-content">
					<div id="canvas" class="yui-navset">
						<@region id="header" scope="page" protected=true />
						<div class="yui-content">
							<div class="content_graph">
								<@region id="day" scope="page" protected=true />
							</div>
							<div class="content_graph">
								<@region id="week" scope="page" protected=true />
							</div>
							<div class="content_graph">
								<@region id="month" scope="page" protected=true />
							</div>
							<div class="content_graph">
								<@region id="year" scope="page" protected=true />
							</div>
						</div>
					</div>
					<div id="dash">
						<div id="Column1">
							<@region id="workflow" scope="page" protected=true />
							<@region id="lock" scope="page" protected=true />
						</div>
						<div id="Column2">
							<@region id="connusr" scope="page" protected=true />
							<@region id="nolog" scope="page" protected=true />
							<@region id="lastusr" scope="page" protected=true />
							
						</div>
						<div id="Column3">
							<@region id="topread" scope="page" protected=true />
							<@region id="topmodif" scope="page" protected=true />
							<@region id="lastup" scope="page" protected=true />
							<@region id="lastmod" scope="page" protected=true />
						</div>
					</div>
					<div id="monit">
						<div id="MonitColumn1">
							<@region id="alfinfo" scope="page" protected=true />
							<@region id="access" scope="page" protected=true />
							<@region id="dbinfo" scope="page" protected=true />	
						</div>
						<div id="MonitColumn2">
							<@region id="runtime" scope="page" protected=true />
							<@region id="contentStores" scope="page" protected=true />
						</div>
						<div id="MonitColumn3">
							<@region id="luceneindexes" scope="page" protected=true />
						</div>
					</div>
					<div id="exp">
						<@region id="export" scope="page" protected=true />
					</div>
				</div>

				<form action="">
					<input type="hidden" value="0" id="d-decount">
					<input type="hidden" value="0" id="w-decount">
					<input type="hidden" value="0" id="m-decount">
					<input type="hidden" value="0" id="y-decount">
				</form>
				<@region id="fileinfo" scope="global" protected=true />
				<@region id="workflowinfo" scope="global" protected=true />
			</div>
			<@script type="text/javascript" src="${url.context}/js/ScrollTabView.js"></@script>
			<@script type="text/javascript" src="${url.context}/js/swfobject.js"></@script>
</@>
<#else>
	<@templateHeader>
		<link rel="stylesheet" type="text/css" href="${url.context}/css/reset-fonts-grids.css" />
		<link rel="stylesheet" type="text/css" href="${url.context}/css/login.css" />
	</@>
	<@templateBody>
	<script type="text/javascript">//<![CDATA[
		Alfresco.util.PopupManager.displayPrompt(
		{
			title: Alfresco.util.message("message.loginfailure"),
			text: Alfresco.util.message("message.noadmin"),
			buttons: [
			{
				text: Alfresco.util.message("button.ok"),
				handler: function error_onOk()
				{
					window.location.replace("${url.context}/page/dologout");
				},
				isDefault: true
			}]
		});
	//]]></script>
	</@>
</#if>