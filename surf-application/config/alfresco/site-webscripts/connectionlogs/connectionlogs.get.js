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
	// retrieve the index of web scripts

	//Get the webscript parameters
	var startdate = args.start;
	var enddate = args.end;
	var noadmin = args.noadmin;
	var limit = args.limit;

	var connectionlogs;

	if(startdate!=null && enddate!=null && noadmin!=null){
		connectionlogs = connector.get("/audit/connectionlogs?start="+startdate+"&end="+enddate+"&noadmin="+noadmin);	
	}
	else if(startdate!=null && enddate!=null){
		connectionlogs = connector.get("/audit/connectionlogs?start="+startdate+"&end="+enddate);	
	}
	else if(noadmin!=null && limit!=null){
		connectionlogs = connector.get("/audit/connectionlogs?noadmin="+noadmin+"&limit="+limit);
	}
	else if(noadmin!=null){
		connectionlogs = connector.get("/audit/connectionlogs?noadmin="+noadmin);
	}
	else if(limit!=null){
		connectionlogs = connector.get("/audit/connectionlogs?limit="+limit);
	}
	else{connectionlogs = connector.get("/audit/connectionlogs");}
		

	var data = eval( '(' + connectionlogs + ')');
	model.items = [];
	var obj;
	
	if(connectionlogs.status != 200){
		if(connectionlogs.status == 401)
			model.errmessage = "invalid";
		else
			model.errmessage = "error";
	}else{			
		for(var i = 0; i<data["item"].length;i++)
		{
			obj = {
				"date" : new Date(parseInt(data.item[i].timestamp)),
				"login" : data.item[i].login,
				"fail" : data.item[i].fail
			};	
			
			model.items.push(obj);	
		}
	}
} catch (error) {
	model.errmessage = "error";	
}	
	