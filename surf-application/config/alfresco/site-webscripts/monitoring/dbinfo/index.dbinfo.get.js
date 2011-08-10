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

try{
	// get a connector to the Alfresco Endpoint
	// this endpoint has a base URI of /alfresco/service
	var connector = remote.connect("alfresco");
	var dbinfo = connector.get("/audit/databaseInfo");

	if(dbinfo.status != 200){
		if(dbinfo.status == 401)
			model.errmessage = "invalid";
		else
			model.errmessage = "error";
	}
	else{
		var data = eval( '(' + dbinfo + ')');

		if (data["Database Product Name"]!=null && data["Database Product Name"]!="")
			model.db_name = data["Database Product Name"];
			
		if (data["Database Product Version"]!=null && data["Database Product Version"]!="")
			model.db_version = data["Database Product Version"];
			
		if (data["Driver Name"]!=null && data["Driver Name"]!="")
			model.dr_name = data["Driver Name"];
			
		if (data["Driver Version"]!=null && data["Driver Version"]!="")
			model.dr_version =data["Driver Version"];
			
		if (data["URL"]!=null && data["URL"]!="")
			model.db_url = data["URL"];
			
		if (data["User Name"]!=null && data["User Name"]!="")
			model.db_username = data["User Name"];
	}
} catch (error) {
	model.errmessage = "error";	
}