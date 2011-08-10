/**
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
* 02110-1301, USA. **/

try {	
	// get a connector to the Alfresco Endpoint
	// this endpoint has a base URI of /alfresco/service
	var connector = remote.connect("alfresco");

	//Get the webscript parameters workflowId
	var workflowId = args.workflowId;

	if(workflowId != "" && workflowId != null && workflowId != "undefined"){
		var workflowinfo = connector.get("/audit/workflowinfo?workflowId="+workflowId);
		
		if(workflowinfo.status != 200){
			if(workflowinfo.status == 401)
				model.errmessage="invalid";
			else
				model.errmessage = "error";
			
			model.code = workflowinfo.status;
		}		
		else{		
			if(workflowinfo != "" && workflowinfo != null){
				var data = eval( '(' + workflowinfo + ')');
				var ids = new Array;
				var values = new Array;
				model.items = [];
				var obj;

				for(var i = 0; i<data["item"].length;i++){
					if(data.item[i].type == "date"){
						obj = {
							"id" : data.item[i].id,
							"value" : new Date(parseInt(data.item[i].value)),
							"type" : data.item[i].type
						};					
					}
					else{
						obj = {
							"id" : data.item[i].id,
							"value" : data.item[i].value,
							"type" : data.item[i].type
						};	
					}
					
					model.items.push(obj);	
				}
			}
		}
	}
} catch (error){
	model.errmessage="error";
	model.code = workflowinfo.status;
}	