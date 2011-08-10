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
<div id="Rec2" class="info">
	<div id="Ctrl_wf_list" class="titremontre hd" ><img src="${url.context}/images/new_workflow.gif" alt="workflow icon"><span class="titlebox">${msg("workflow")}</span></div>
	<@refresh.displayRefreshButton />
	<div onClick="Display(this)" id="bt_wf_list" class="button buttonUp"></div>
	<div id="wf_list" class="montre bd">
		<ul>
			<#if workflows?exists>
				<#assign cpt = 0 />
				
				<#list workflows?sort_by("date")?reverse as workflow>
					<#if cpt &lt; config.script.workflow.maxdisplay?number>
						<li>
							<#if workflow.status?exists>
							<#assign statusname = workflow.status?lower_case?replace(" ","-") />
								<img src="${url.context}/${getIconStatus(workflow.status)}" alt="workflow status icon" title="${msg("status-${statusname}")}">
							</#if>
							<a class="workflow_link" onclick="printInfoWorkflow(this, '${workflow.workflowId}')" >
								<#if workflow.type?exists>
									<#if workflow.type == "">Workflow<#else>${workflow.type}</#if>
								</#if>
								${msg("init")}<#if workflow.firstname?exists>${workflow.firstname}</#if> <#if workflow.lastname?exists>${workflow.lastname}</#if><#-- Workflow info -->
							</a><br>
							<p class="workflow_p">
							${msg("start")}${workflow.date?datetime?string(msg("mask"))}
							</p>
							<#-- <#if workflow.username?exists>${workflow.username}</#if> -->
						</li>
					<#else>
						<#break>						
					</#if>	
					<#assign cpt = cpt + 1 />
				</#list>
			<#else>
				<#if errmessage?exists>
					<div class="${errmessage}">${msg("${errmessage?string}")}</div>
				</#if>				
			</#if>		
		</ul>		
	</div>
</div>


<#function getIconStatus status>
	<#assign icon = "images/workflow_default.png" />
	
	<#if status == "Not Yet Started" || status == "draft">
		<#assign icon = "images/workflow_not_yet_started.png" />
		
	<#elseif status == "In Progress" || status == "ongoing">
		<#assign icon = "images/workflow_in_progress.png" />
		
	<#elseif status == "On Hold" || status == "validating">
		<#assign icon = "images/workflow_on_hold.png" />
		
	<#elseif status == "Cancelled" || status == "rejected">
		<#assign icon = "images/workflow_cancelled.png" />
		
	<#elseif status == "Completed" || status == "finished">
		<#assign icon = "images/workflow_completed.png" />
	</#if>
	
	<#return icon>
</#function>