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

<@templateHeader>
  <link rel="stylesheet" type="text/css" href="${url.context}/css/reset-fonts-grids.css" />
  <link rel="stylesheet" type="text/css" href="${url.context}/css/login.css" />
</@>
<@templateBody>
   <div id="alflogin" class="login-panel">
      <div class="login-logo"></div>
      <form id="loginform" accept-charset="UTF-8" method="post" action="${url.context}/page/dologin" onsubmit="return alfLogin();">
         <fieldset>
            <div style="padding-top:96px">
               <label id="txt-username" for="username">${msg('label.username')}</label>
            </div>
            <div style="padding-top:4px">
               <input type="text" id="username" name="username" maxlength="256" style="width:200px"/>
            </div>
            <div style="padding-top:12px">
               <label id="txt-password" for="password">${msg('label.password')}</label>
            </div>
            <div style="padding-top:4px">
               <input type="password" id="password" name="password" maxlength="256" style="width:200px"/>
            </div>
            <div style="padding-top:16px">
               <input type="submit" id="btn-login" class="login-button"></input>
            </div>
            <div style="padding-top:26px">
               <span class="login-copyright">
                  &copy; 2010 Atol Conseils et Développements.
               </span>
            </div>
            <input type="hidden" id="success" name="success" value="${successUrl}"/>
            <input type="hidden" name="failure" value="<#assign link>${url.context}/page/type/login</#assign>${link?html}?error=true"/>
         </fieldset>
      </form>
   </div>
   
   <script type="text/javascript">//<![CDATA[
   function alfLogin()
   {
      YAHOO.util.Dom.get("btn-login").setAttribute("disabled", true);
      return true;
   }
   
   YAHOO.util.Event.onContentReady("alflogin", function()
   {
      var Dom = YAHOO.util.Dom;   
      // Prevent the Enter key from causing a double form submission
      var form = Dom.get("loginform");
      // add the event to the form and make the scope of the handler this form.
      YAHOO.util.Event.addListener(form, "submit", this._submitInvoked, this, true);
      var fnStopEvent = function(id, keyEvent)
      {
         if (form.getAttribute("alflogin") == null)
         {
            form.setAttribute("alflogin", true);
         }
         else
         {
            form.attributes.action.nodeValue = "";
         }
      }
      
      var enterListener = new YAHOO.util.KeyListener(form,
      {
         keys: YAHOO.util.KeyListener.KEY.ENTER
      }, fnStopEvent, "keydown");
      enterListener.enable();
      
      // set I18N labels
      Dom.get("btn-login").value = Alfresco.util.message("button.login");
	  
      // generate and display main login panel
      var panel = new YAHOO.widget.Overlay(YAHOO.util.Dom.get("alflogin"), 
      {
         modal: false,
         draggable: false,
         fixedcenter: true,
         close: false,
         visible: true,
         iframe: false
      });
      panel.render(document.body);
      
      Dom.get("success").value += window.location.hash;
      Dom.get("username").focus();
   });
   
	<#if url.args["error"]??>
	   Alfresco.util.PopupManager.displayPrompt(
	   {
		  title: Alfresco.util.message("message.loginfailure"),
		  text: Alfresco.util.message("message.loginautherror"),
		  buttons: [
		  {
			 text: Alfresco.util.message("button.ok"),
			 handler: function error_onOk()
			 {
				this.destroy();
				YAHOO.util.Dom.get("username").focus();
				YAHOO.util.Dom.get("username").select();
			 },
			 isDefault: true
		  }]
	   });
	</#if>
   //]]></script>
</@>
</body>
</html>