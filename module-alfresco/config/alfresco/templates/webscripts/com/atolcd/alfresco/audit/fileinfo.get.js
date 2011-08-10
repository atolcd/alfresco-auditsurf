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

function getSiteShortName(node){
	if( node.qnamePath.indexOf("st:site") == -1){
		return null ;
	}

	var pathSplit = node.qnamePath.split("/");

	if(pathSplit.length>=4){
		return pathSplit[3].split(":").reverse()[0];
	}
}

var nodeRef = args.nodeRef;

if(nodeRef){
	var node = search.findNode(nodeRef);
	if(node != null){
		model.node = node ;
		var shortName = getSiteShortName(node);
		if(shortName){
			var site = siteService.getSite(shortName);
			model.site = site;
		}else
			logger.log("shortName doesn't exist");
	}
	else
		logger.log("node doesn't exist");
}
else
	logger.log("nodeRef doesn't exist");
