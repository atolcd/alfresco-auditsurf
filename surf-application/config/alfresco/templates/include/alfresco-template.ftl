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

<#import "../import/alfresco-common.ftl" as common />

<#-- Global flags retrieved from web-framework-config-application -->
<#assign DEBUG=(common.globalConfig("client-debug", "false") = "true")>
<#assign AUTOLOGGING=(common.globalConfig("client-debug-autologging", "false") = "true")>

<#-- Look up page title from message bundles where possible -->
<#assign pageTitle = page.title />
<#if page.titleId??>
   <#assign pageTitle = (msg(page.titleId))!page.title>
</#if>
<#if context.properties["page-titleId"]??>
   <#assign pageTitle = msg(context.properties["page-titleId"])>
</#if>

<#--
   JavaScript minimisation via YUI Compressor.
-->
<#macro script type src>
   <script type="${type}" src="${DEBUG?string(src, src?replace('.js', '-min.js'))}"></script>
</#macro>

<#--
   Template "templateHeader" macro.
   Includes preloaded YUI assets and essential site-wide libraries.
-->                                                                           
<#macro templateHeader doctype="strict">
   <#if doctype = "strict">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
   <#else>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
   </#if>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <title>Alfresco Audit &raquo; ${page.title}</title>

<!-- Shortcut Icons -->
   <link rel="shortcut icon" href="${url.context}/images/favicon.ico" type="image/vnd.microsoft.icon" /> 
   <link rel="icon" href="${url.context}/images/favicon.ico" type="image/vnd.microsoft.icon" />

<#-- Selected components preloaded here for better UI experience. -->
<!-- Common YUI components: RELEASE concatenated -->
    <@script type="text/javascript" src="${url.context}/js/combo_yahoo.js"></@script>

<!-- Site-wide Common Assets -->
   <@link rel="stylesheet" type="text/css" href="${url.context}/css/base.css" />
   <@script type="text/javascript" src="${url.context}/js/bubbling.v2.1.js"></@script>
   <script type="text/javascript">//<![CDATA[
      YAHOO.Bubbling.unsubscribe = function(layer, handler)
      {
         this.bubble[layer].unsubscribe(handler);
      }
   //]]></script>
   
   <#-- NOTE: Do not attempt to load -min.js version of messages.js -->
   <script type="text/javascript" src="${url.context}/service/messages.js?locale=${locale}"></script>
   <@script type="text/javascript" src="${url.context}/js/alfresco.js"></@script>
   <script type="text/javascript">//<![CDATA[
      Alfresco.constants = Alfresco.constants || {};
      Alfresco.constants.DEBUG = ${DEBUG?string};
      Alfresco.constants.AUTOLOGGING = ${AUTOLOGGING?string};
      Alfresco.constants.PROXY_URI = window.location.protocol + "//" + window.location.host + "${url.context}/proxy/alfresco/";
      Alfresco.constants.PROXY_URI_RELATIVE = "${url.context}/proxy/alfresco/";
      Alfresco.constants.PROXY_FEED_URI = window.location.protocol + "//" + window.location.host + "${url.context}/proxy/alfresco-feed/";
      Alfresco.constants.THEME = "${theme}";
      Alfresco.constants.URL_CONTEXT = "${url.context}/";
      Alfresco.constants.URL_PAGECONTEXT = "${url.context}/page/";
      Alfresco.constants.URL_SERVICECONTEXT = "${url.context}/service/";
      Alfresco.constants.URL_FEEDSERVICECONTEXT = "${url.context}/feedservice/";
      Alfresco.constants.USERNAME = "${user.name!""}";
   //]]></script>

<!-- Template Assets -->
<#nested>

<!-- Component Assets -->
${head}

</head>
</#macro>

<#--
   Template "templateBody" macro.
   Pulls in main template body.
-->
<#macro templateBody>
<body id="AuditSurf" class="yui-skin-${theme}">
   <div class="sticky-wrapper">
      <div id="doc3">
<#-- Template-specific body markup -->
<#nested>
      </div>
      <div class="sticky-push"></div>
   </div>
</#macro>


<#--
   Template "templateFooter" macro.
   Pulls in template footer.
-->
<#macro templateFooter>
   <div class="sticky-footer">
<#-- Template-specific footer markup -->
<#nested>
   </div>
<#-- This function call MUST come after all other component includes. -->
   <div id="alfresco-yuiloader"></div>
   <script type="text/javascript">//<![CDATA[
      Alfresco.util.YUILoaderHelper.loadComponents();
   //]]></script>
</body>
</html>
</#macro>