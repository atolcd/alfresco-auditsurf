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

model.items = [];
var item, obj;

var iterators = ActiveWorkflow.iterator();

while(iterators.hasNext())
{
	var it = iterators.next();
	
	var key = it.getKey();
	var values = new Array();
	values = it.getValue();

	obj = {
		"date" : key,
		"key" : key.getTime().toString(),
		"type" : values[0],
		"firstname" : values[1],
		"lastname" : values[2],
		"username" : values[3],
		"status" : values[4],
		"workflowId" : values[5]
	};
	
	model.items.push(obj);
}