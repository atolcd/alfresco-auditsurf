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

	//Get the webscript parameters period & date
	var period = args.period;
	var date = args.date;
	if(args.big==null || args.big=="" || args.big=="undefined")
		var big = false;
	else
		var big = true;

	var raw = connector.get("/audit/documents?period="+ period +"&date="+ date +"");
	var data = eval( '(' + raw + ')');
	var dates= new Array;
	var crea= new Array;
	var mod= new Array;
	var read= new Array;
	
	for(var i = 0; i<data.item["crea"].length;i++){
		var day = parseInt(data.item["crea"][i].date);
		dates[i]= new Date(day);
		crea[i]=parseInt(data.item.crea[i].value);
		mod[i]=parseInt(data.item.mod[i].value);
		read[i]=parseInt(data.item.read[i].value);
	}
	
	model.date=dates;
	model.crea = crea;
	model.mod = mod;
	model.read = read;
	model.big = big;
}
catch(error){
	model.error = "error";
	model.code = raw.status.code;
}