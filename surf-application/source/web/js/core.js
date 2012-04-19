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

/**** Get cookies for charts places ****/
	order = new Array;
	if(YAHOO.util.Cookie.get("place0"))
	{
		order[0] = YAHOO.util.Cookie.get("place0");
		order[1] = YAHOO.util.Cookie.get("place1");
		order[2] = YAHOO.util.Cookie.get("place2");
		order[3] = YAHOO.util.Cookie.get("place3");
	}
	else
	{
		order[0] = "OFCDocuments/ofcdocuments";
		order[1] = "OFCUsersCreations/ofcuserscreations";
		order[2] = "OFCWorkflows/ofcworkflows";
		order[3] = "OFCConnections/ofcconnections";
	}

/**** Set cookies for charts places & change order values ****/
	function OrderCharts(up,period){
		//Get the current value of the big chart
		var old = order[0];
		//Put in the big chart the chart which trigged the event
		order[0] = order[up];
		//Put the old value of the big chart in place of the new
		order[up] = old;
		
		//Set the cookies
		YAHOO.util.Cookie.set("place0", order[0]); 
		YAHOO.util.Cookie.set("place1", order[1]); 
		YAHOO.util.Cookie.set("place2", order[2]); 
		YAHOO.util.Cookie.set("place3", order[3]); 
		
		//Call PrintGraph to refresh
		var decount = parseInt(document.getElementById(period+'-decount').value);
		switch(period){
		case 'd' :
			var per = "day";
		break;
		
		case 'w' :
			var per = "week";
		break;
		
		case 'm' :
		var per = "month";
		break;
		
		case 'y' :
			var per = "year";
		break;		
		}
		printGraphs(per,decount,up);
	}

/**** Interface initialization ****/
window.onload = function(){

	var tabs = new YAHOO.yodeler.widget.ScrollTabView('global', { 
						duration: 0.5, 
						easing: YAHOO.util.Easing.easeIn, 
						width: ((document.body.clientWidth)-30), 
						height: '1000', 
						direction: 'horizontal' });
	tabs.set('activeTab', tabs.getTab(0));
		
	var tabs = new YAHOO.yodeler.widget.ScrollTabView('canvas', { 
						duration: 0.5, 
						easing: YAHOO.util.Easing.easeIn, 
						width: ((document.body.clientWidth)-30), 
						height: '1350', 
						direction: 'vertical' });
	tabs.set('activeTab', tabs.getTab(1));
	
	// Loading "week charts"
	printGraphs("week",0,0)
	
	// Initialization of information's blocs (Show/Hide click)
	Init('bt_access');
	Init('bt_alf_info');
	Init('bt_contentStores');
	Init('bt_dbinfo');
	Init('bt_luceneindexes');
	Init('bt_runtime');
	
	
	// Initialization of Lucene bloc (monitoring)
	var bloc_lucene = document.getElementById('bloc_lucene');
	if (bloc_lucene) {
		for (var i=0; i<bloc_lucene.childNodes.length; i++){
			// search elements of class lucene_li
			if (bloc_lucene.childNodes[i].className == "lucene_li"){
				for (var j=0; j<bloc_lucene.childNodes[i].childNodes.length; j++){
					// search elements of class title_lucene
					if (bloc_lucene.childNodes[i].childNodes[j].className == "title_lucene"){
						// add action on the title
						bloc_lucene.childNodes[i].childNodes[j].onclick = function() { Lucene(this.id); return false; };
						break;
					}
				}
			}
		}
	}

	// Reset and initailize inputs
	document.getElementById('d-decount').value = 0;
	document.getElementById('w-decount').value = 0;
	document.getElementById('m-decount').value = 0;
	document.getElementById('y-decount').value = 0;
		
	document.getElementById("checkbutton1").checked = false;
	document.getElementById("checkbutton2").checked = false;
	document.getElementById("checkbutton3").checked = false;
	document.getElementById("checkbutton4").checked = false;
	document.getElementById("checkbutton5").checked = false;
	document.getElementById("checkbutton6").checked = false;
	document.getElementById("radio1").checked = true;
	document.getElementById("radio2").checked = false;
	document.getElementById("radio3").checked = false;
	document.getElementById("year").value = 0;
	document.getElementById("year2").value = 0;
	document.getElementById("buttongroup1").value = "json";
		
	
	// Init buttons of menubar (Used to hide panelInfos)
	document.getElementById('menu_canvas').onclick = function(){changeMenu();return false; };
	document.getElementById('menu_dash').onclick = function(){changeMenu();return false; };
	document.getElementById('menu_monit').onclick = function(){changeMenu();return false; };
	document.getElementById('menu_exp').onclick = function(){changeMenu();return false; };
	
	
	// Get nav links and add OnClick event
	var d = document.getElementById('a_d');
	d.onclick = function(){printGraphs("day",document.getElementById('d-decount').value,0);return false; };	
	
	var w = document.getElementById('a_w');
	w.onclick = function(){printGraphs("week",document.getElementById('w-decount').value,0);return false; };		
	
	var m = document.getElementById('a_m');
	m.onclick = function(){printGraphs("month",document.getElementById('m-decount').value,0);return false; };
		
	var y = document.getElementById('a_y');
	y.onclick = function(){printGraphs("year",document.getElementById('y-decount').value,0);return false; };

		
	// Get nav arrows for charts & add OnClick
	var prev_d = document.getElementById('prev-d');
	prev_d.onclick = function(){NavChart('p-d');return false;};
		
	var init_d = document.getElementById('init-d');
	init_d.onclick = function(){NavChart('i-d');return false;};
		
	var next_d = document.getElementById('next-d');
	next_d.onclick = function(){NavChart('n-d');return false;};
		
	var prev_w = document.getElementById('prev-w');
	prev_w.onclick = function(){NavChart('p-w');return false;};
		
	var init_w = document.getElementById('init-w');
	init_w.onclick = function(){NavChart('i-w');return false;};
		
	var next_w = document.getElementById('next-w');
	next_w.onclick = function(){NavChart('n-w');return false;};
		
	var prev_m = document.getElementById('prev-m');
	prev_m.onclick = function(){NavChart('p-m');return false;};
		
	var init_m = document.getElementById('init-m');
	init_m.onclick = function(){NavChart('i-m');return false;};
		
	var next_m = document.getElementById('next-m');
	next_m.onclick = function(){NavChart('n-m');return false;};
		
	var prev_y = document.getElementById('prev-y');
	prev_y.onclick = function(){NavChart('p-y');return false;};
		
	var init_y = document.getElementById('init-y');
	init_y.onclick = function(){NavChart('i-y');return false;};
		
	var next_y = document.getElementById('next-y');
	next_y.onclick = function(){NavChart('n-y');return false;};
	
	// Get UpChart elements
	var up_1_d = document.getElementById('up1_d');
	up_1_d.onclick = function(){OrderCharts(1,'d');return false;};
	
	var up_2_d = document.getElementById('up2_d');
	up_2_d.onclick = function(){OrderCharts(2,'d');return false;};
	
	var up_3_d = document.getElementById('up3_d');
	up_3_d.onclick = function(){OrderCharts(3,'d');return false;};
	
	var up_1_w = document.getElementById('up1_w');
	up_1_w.onclick = function(){OrderCharts(1,'w');return false;};
	
	var up_2_w = document.getElementById('up2_w');
	up_2_w.onclick = function(){OrderCharts(2,'w');return false;};
	
	var up_3_w = document.getElementById('up3_w');
	up_3_w.onclick = function(){OrderCharts(3,'w');return false;};
	
	var up_1_m = document.getElementById('up1_m');
	up_1_m.onclick = function(){OrderCharts(1,'m');return false;};
	
	var up_2_m = document.getElementById('up2_m');
	up_2_m.onclick = function(){OrderCharts(2,'m');return false;};
	
	var up_3_m = document.getElementById('up3_m');
	up_3_m.onclick = function(){OrderCharts(3,'m');return false;};

	var up_1_y = document.getElementById('up1_y');
	up_1_y.onclick = function(){OrderCharts(1,'y');return false;};
	
	var up_2_y = document.getElementById('up2_y');
	up_2_y.onclick = function(){OrderCharts(2,'y');return false;};
	
	var up_3_y = document.getElementById('up3_y');
	up_3_y.onclick = function(){OrderCharts(3,'y');return false;};		
	
	// Get ok-button to process the form
	//var exp = document.getElementById('ok-button-button');
	//exp.onclick = function(){GenerateExportURL();return false;};
};

/**** Onclick Events ****/
function Init(v){
	var t = document.getElementById(v);
	t.onclick = function() { Display(this); return false; };    //ctrl,id,v    
};

/**
* Test if ticket has expired
*/
function testTicket(){
	var xhr = getXhr();
	var url = "./testticket/testticket";
		
	xhr.onreadystatechange = function(){
		// if server Ok
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById("testticket").innerHTML = xhr.responseText;
		}
	}
	xhr.open("GET", url, true);
	xhr.send(null);
}	


/**
* Refresh Dashlet Functions
*/
function imgChange(img_node, img_src){
	try{
		img_node.src = img_src;
	}catch(e){}
}

function refreshDashlet(url, div_id){
	var xhr = getXhr();
	var fullUrl;
	if (url.split('?').length == 1)
		fullUrl = url+"?htmlId="+div_id;
	else
		fullUrl = url+"&htmlId="+div_id;
	
	xhr.onreadystatechange = function(){
		// if server Ok
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById(div_id).innerHTML = xhr.responseText;
		}
	}
	xhr.open("GET", fullUrl, true);
	xhr.send(null);
}

function refreshDashletWithLoading(url, div_id, id){
	try{
		document.getElementById(id).innerHTML = "<div class=\"loading\"><img src=\"../images/loading2.gif\" alt=\"\" /></div>";
		//** HACK **/
		document.getElementById(div_id).getElementsByTagName('div')[4].innerHTML = "<div class=\"loading\"><img src=\"../images/loading2.gif\" alt=\"\" /></div>";
	}catch(e){}

	refreshDashlet(url, div_id);
}


/**** Show/Hide informations for any blocs ****/
function Display(v){
	for(i=0; i<v.parentNode.childNodes.length; i++){
		var t = v.parentNode.childNodes[i];
		
		if (t.className == "titremontre hd" || t.className == "titrecache hd")
			t.className = (t.className == 'titrecache hd') ? 'titremontre hd' : 'titrecache hd' ;
		else if(t.className=='cache bd' || t.className=='montre bd')
			t.className = (t.className == 'cache bd') ? 'montre bd' : 'cache bd';
		else if(t.className=='button buttonDown' || t.className=='button buttonUp')
			t.className = (t.className == 'button buttonDown') ? 'button buttonUp' : 'button buttonDown';
		
	}
}

/**** Show/Hide informations of Lucene blocs (monitoring panel) ****/
function Lucene(r){
	var t = document.getElementById('bloc_lucene');
	
	for (var i=0; i<t.childNodes.length; i++){
		var z = t.childNodes[i];
		
		if (z.nodeName == "LI" && z.className == "lucene_li"){
			var find = false;
			for (var j=0; j<z.childNodes.length; j++){
				// if this title is selected
				if (z.childNodes[j].className == "title_lucene" && z.childNodes[j].id == r){
					find = true;
					// print the button
					y = z.childNodes[j];
					for (var k=0; k<y.childNodes.length; k++){
						if (y.childNodes[k].className=="bt_lucene buttonDown" || y.childNodes[k].className=="bt_lucene buttonUp")
							if (y.childNodes[k].className == "bt_lucene buttonDown")
								y.childNodes[k].className = "bt_lucene buttonUp";
							else
								y.childNodes[k].className = "bt_lucene buttonDown";
					}
				}
				
				// show or hide content
				if (find == true && (z.childNodes[j].className == "content_lucene cache" || z.childNodes[j].className == "content_lucene montre")){
					if (z.childNodes[j].className == "content_lucene cache")
						z.childNodes[j].className = "content_lucene montre";
					else
						z.childNodes[j].className = "content_lucene cache";
				}
			}
		}
	}
}

/**** Refresh Charts  ****/
	function NavChart(p){
		switch(p){
			case 'p-d' :
				var period = "day";
				var decount = parseInt(document.getElementById('d-decount').value) + 1;
				document.getElementById('d-decount').value = decount;
				printGraphs(period,decount,0);
			break;
			
			case 'i-d' :
				var period = "day";
				var decount = 0;
				if (parseInt(document.getElementById('d-decount').value)>0)
				{
				document.getElementById('d-decount').value = decount;
				printGraphs(period,decount,0);
				}
			break;
			
			case 'n-d' :
				var period = "day";
				var decount = parseInt(document.getElementById('d-decount').value) - 1;
				if(decount >=0)
				{
					document.getElementById('d-decount').value = decount;
					printGraphs(period,decount,0);
				}
			break;
			
			case 'p-w' :
				var period = "week";
				var decount = parseInt(document.getElementById('w-decount').value) + 1;
				document.getElementById('w-decount').value = decount;
				printGraphs(period,decount,0);
			break;
			
			case 'i-w' :
				var period = "week";
				var decount = 0;
				if (parseInt(document.getElementById('w-decount').value)>0)
				{
				document.getElementById('w-decount').value = decount;
				printGraphs(period,decount,0);
				}
			break;
			
			case 'n-w' :
				var period = "week";
				var decount = parseInt(document.getElementById('w-decount').value) - 1;
				if(decount >=0)
				{
					document.getElementById('w-decount').value = decount;
					printGraphs(period,decount,0);
				}
			break;
			
			case 'p-m' :
				var period = "month";
				var decount = parseInt(document.getElementById('m-decount').value) + 1;
				document.getElementById('m-decount').value = decount;
				printGraphs(period,decount,0);
			break;
			
			case 'i-m' :
				var period = "month";
				var decount = 0;
				if (parseInt(document.getElementById('m-decount').value)>0)
				{
				document.getElementById('m-decount').value = decount;
				printGraphs(period,decount,0);
				}
			break;
			
			case 'n-m' :
				var period = "month";
				var decount = parseInt(document.getElementById('m-decount').value) - 1;
				if(decount >=0)
				{
					document.getElementById('m-decount').value = decount;
					printGraphs(period,decount,0);
				}
			break;
			
			case 'p-y' :
				var period = "year";
				var decount = parseInt(document.getElementById('y-decount').value) + 1;
				document.getElementById('y-decount').value = decount;
				printGraphs(period,decount,0);
			break;
			
			case 'i-y' :
				var period = "year";
				var decount = 0;
				if (parseInt(document.getElementById('y-decount').value)>0)
				{
				document.getElementById('y-decount').value = decount;
				printGraphs(period,decount,0);
				}
			break;
			
			case 'n-y' :
				var period = "year";
				var decount = parseInt(document.getElementById('y-decount').value) - 1;
				if(decount >=0)
				{
					document.getElementById('y-decount').value = decount;	
					printGraphs(period,decount,0);
				}
			break;
			
			default :
			break;
		}

	}

/**** Load chart for the wanted period  ****/
	function printGraphs(period, decount,all){
		testTicket();
		var chart1, chart2, chart3, chart4;
		switch(period){
			case 'day' :
				chart1 = "my_chart5";
				chart2 = "my_chart";
				chart3 = "my_chart21";
				chart4 = "my_chart25";
			break;
			case 'week' :
				chart1 = "my_chart6";
				chart2 = "my_chart2";
				chart3 = "my_chart22";
				chart4 = "my_chart26";
			break;
			case 'month' :
				chart1 = "my_chart7";
				chart2 = "my_chart3";
				chart3 = "my_chart23";
				chart4 = "my_chart27";
			break;
			case 'year' :
				chart1 = "my_chart8";
				chart2 = "my_chart4";
				chart3 = "my_chart24";
				chart4 = "my_chart28";
			break;
		}
		
		//Refreshing all the charts
		if(all==0)
		{
			swfobject.embedSWF(
				"../swf/open-flash-chart.swf", chart2, ((document.body.clientWidth)-30)*0.3, ((document.body.clientWidth)-30)*0.2,
				"9.0.0", "expressInstall.swf",
				{"data-file":encodeURIComponent("./ofc/"+order[2]+"?period="+period+"&date="+decount)}
			);
			swfobject.embedSWF(
				"../swf/open-flash-chart.swf", chart1, ((document.body.clientWidth)-30)*0.3, ((document.body.clientWidth)-30)*0.2,
				"9.0.0", "expressInstall.swf",
				{"data-file":encodeURIComponent("./ofc/"+order[1]+"?period="+period+"&date="+decount)}
			);
			swfobject.embedSWF(
				"../swf/open-flash-chart.swf", chart3, ((document.body.clientWidth)-30)*0.3, ((document.body.clientWidth)-30)*0.2,
				"9.0.0", "expressInstall.swf",
				{"data-file":encodeURIComponent("./ofc/"+order[3]+"?period="+period+"&date="+decount)}
			);
			swfobject.embedSWF(
				"../swf/open-flash-chart.swf", chart4, ((document.body.clientWidth)-30)*0.9, ((document.body.clientWidth)-30)*0.35,
				"9.0.0", "expressInstall.swf",
				{"data-file":encodeURIComponent("./ofc/"+order[0]+"?period="+period+"&date="+decount+"&big=yes")}
			);
		}
		//Only refreshing the big chart and the "swapped" one
		else
		{
			var ch_up = eval("chart"+all);
			
			swfobject.embedSWF(
				"../swf/open-flash-chart.swf", chart4, ((document.body.clientWidth)-30)*0.9, ((document.body.clientWidth)-30)*0.35,
				"9.0.0", "expressInstall.swf",
				{"data-file":encodeURIComponent("./ofc/"+order[0]+"?period="+period+"&date="+decount+"&big=yes")}
			);
			
			swfobject.embedSWF(
				"../swf/open-flash-chart.swf", ch_up, ((document.body.clientWidth)-30)*0.3, ((document.body.clientWidth)-30)*0.2,
				"9.0.0", "expressInstall.swf",
				{"data-file":encodeURIComponent("./ofc/"+order[all]+"?period="+period+"&date="+decount)}
			);			
		}
	}

/**** Generate the Webscript URL ****/
function GenerateExportURL(){
	//Validation
	var valid = true;
	if(document.getElementById("year").value==0)
	{
		valid = false;
		// danger : function(oEl, iOffset, iNum, iSpeed)
		effects.danger("calendarpicker-button", 6, 6, 0.2);
	}
	if(document.getElementById("year2").value==0)
	{
			valid = false;
		effects.danger("calendarpicker2-button", 6, 6, 0.2);
	}
	
	if(valid)
	{
		var strt = document.getElementById("day").value+"-"+document.getElementById("month").value+"-"+document.getElementById("year").value;
		var fnsh = document.getElementById("day2").value+"-"+document.getElementById("month2").value+"-"+document.getElementById("year2").value;
		var check  = document.getElementById("checkbutton1").checked+"&modifieddocs="+document.getElementById("checkbutton2").checked+"&readdocs="+document.getElementById("checkbutton3").checked+"&conn="+document.getElementById("checkbutton4").checked+"&createduser="+document.getElementById("checkbutton5").checked+"&createdwork="+document.getElementById("checkbutton6").checked;
		var out =document.getElementById("buttongroup1").value;
		var url = "?start="+strt+"&stop="+fnsh+"&createddocs="+check;
		window.open("./proxy/alfresco/audit/export."+out+url,"Export","scrollbars=1, menubar=yes"); 
	}
}
	
/**** Shake effect function ****/
effects = function() {
	var $D = YAHOO.util.Dom;
	var $M = YAHOO.util.Motion;
	return {
	/*
			@param {String/HTMLElement} | oEl : Accepts a string to use as an ID for getting a DOM reference, or an actual DOM reference
			@param {Int} | iOffset : The unit (in 'px') that the element will be shaken 'by'
			@param {Int} | iNum : The number of times the motion will iterate
			@param {Int} | iSpeed : The speed at which the motion will animate
		*/
		danger : function(oEl, iOffset, iNum, iSpeed) {
			var xy = $D.getXY(oEl);
			var left = xy[0]-iOffset;
			var right = xy[0]+iOffset;
			(function(type, args, count) {
				if ( count >= iNum ) {
					var a = { 
						points : {
							to : xy
						}
					};
					var anim = new $M(oEl, a, iSpeed);
					anim.animate();
					return;
				}
				else if ( count % 2 ) {
					var c = count+1;
					var a = { 
						points : {
							to : [right, xy[1]]
						}
					};
					var anim = new $M(oEl, a, iSpeed);
					anim.onComplete.subscribe(arguments.callee, c);
					anim.animate();
				}
				else {
					var c = count+1;
					var a = { 
						points : {
							to : [left, xy[1]]
						}
					};
					var anim = new $M(oEl, a, iSpeed);
					anim.onComplete.subscribe(arguments.callee, c);
					anim.animate();
				}
			})(null, null, 1);
		}
	};
}();
	
/**** Button export script ****/
    YAHOO.util.Event.onContentReady("buttoncontainer", function () {

		// Create a new Button instance
		var oPushButton = new YAHOO.widget.Button({ 
                            label: Alfresco.util.message("export"), 
                            id:"exp_bt", 
                            container:"buttoncontainer" });
		// "click" event handler for each Button instance
		oPushButton.on("click", GenerateExportURL);
		});

/**** MenuBar script ****/
	function changeMenu(){
		YAHOO.fileInfo.container.panelInfo.hide();			// hide the panel
		YAHOO.fileInfo.container.panelInfoWorkflow.hide();	// hide the panel
	}

/**** Calendar scripts ****/
//<![CDATA[

(function () {

var Event = YAHOO.util.Event,
	Dom = YAHOO.util.Dom;   


Event.onContentReady("datefields", function () {

	var oCalendarMenu;
	var onButtonClick = function () {

			// Create a Calendar instance and render it into the body 
			// element of the Overlay.
			var oCalendar = new YAHOO.widget.Calendar("buttoncalendar", oCalendarMenu.body.id);
			oCalendar.render();


			// Subscribe to the Calendar instance's "select" event to 
			// update the Button instance's label when the user
			// selects a date.
			oCalendar.selectEvent.subscribe(function (p_sType, p_aArgs) {

					var aDate,
							nMonth,
							nDay,
							nYear;

					if (p_aArgs) {
							
							aDate = p_aArgs[0][0];

							nMonth = aDate[1];
							nDay = aDate[2];
							nYear = aDate[0];

							oButton.set("label", (nMonth + "/" + nDay + "/" + nYear));


							// Sync the Calendar instance's selected date with the date form fields

							Dom.get("month").selectedIndex = (nMonth - 1);
							Dom.get("day").selectedIndex = (nDay - 1);
							Dom.get("year").value = nYear;

					}
					
					oCalendarMenu.hide();
			
			});


			// Pressing the Esc key will hide the Calendar Menu and send focus back to 
			// its parent Button
			Event.on(oCalendarMenu.element, "keydown", function (p_oEvent) {
			
					if (Event.getCharCode(p_oEvent) === 27) {
							oCalendarMenu.hide();
							this.focus();
					}
			
			}, null, this);
			
			
			var focusDay = function () {

					var oCalendarTBody = Dom.get("buttoncalendar").tBodies[0],
							aElements = oCalendarTBody.getElementsByTagName("a"),
							oAnchor;
					
					if (aElements.length > 0) {
					
							Dom.batch(aElements, function (element) {
							
									if (Dom.hasClass(element.parentNode, "today")) {
											oAnchor = element;
									}
							
							});
							
							
							if (!oAnchor) {
									oAnchor = aElements[0];
							}


							// Focus the anchor element using a timer since Calendar will try 
							// to set focus to its next button by default
							YAHOO.lang.later(0, oAnchor, function () {
									try {
											oAnchor.focus();
									}
									catch(e) {}
							});
					
					}
					
			};


			// Set focus to either the current day, or first day of the month in 
			// the Calendar when it is made visible or the month changes
			oCalendarMenu.subscribe("show", focusDay);
			oCalendar.renderEvent.subscribe(focusDay, oCalendar, true);


			// Give the Calendar an initial focus
			focusDay.call(oCalendar);


			// Re-align the CalendarMenu to the Button to ensure that it is in the correct
			// position when it is initial made visible
			oCalendarMenu.align();


			// Unsubscribe from the "click" event so that this code is 
			// only executed once
			this.unsubscribe("click", onButtonClick);
	
	};


	var oDateFields = Dom.get("datefields");
			oMonthField = Dom.get("month"),
			oDayField = Dom.get("day"),
			oYearField = Dom.get("year");


	// Hide the form fields used for the date so that they can be replaced by the 
	// calendar button.
	oMonthField.style.display = "none";
	oDayField.style.display = "none";
	oYearField.style.display = "none";


	// Create a Overlay instance to house the Calendar instance
	oCalendarMenu = new YAHOO.widget.Overlay("calendarmenu", { visible: false });

	// Create a Button instance of type "menu"
	var oButton = new YAHOO.widget.Button({ 
			type: "menu", 
			id: "calendarpicker", 
			label: Alfresco.util.message("start.date"), 
			menu: oCalendarMenu, 
			container: "datefields" });
																	
	oButton.on("appendTo", function () {
		// Create an empty body element for the Overlay instance in order 
		// to reserve space to render the Calendar instance into.
		oCalendarMenu.setBody("&#32;");
		oCalendarMenu.body.id = "calendarcontainer";	
	});


	// Add a listener for the "click" event.  This listener will be
	// used to defer the creation the Calendar instance until the 
	// first time the Button's Overlay instance is requested to be displayed
	// by the user.
	oButton.on("click", onButtonClick);

});

}());

//]]>

/**** Calendar ****/
//<![CDATA[

(function () {

var Event = YAHOO.util.Event,
	Dom = YAHOO.util.Dom;   


Event.onContentReady("datefields2", function () {

	var oCalendarMenu2;

	var onButtonClick2 = function () {

			// Create a Calendar instance and render it into the body 
			// element of the Overlay.
			var oCalendar2 = new YAHOO.widget.Calendar("buttoncalendar2", oCalendarMenu2.body.id);
			oCalendar2.render();


			// Subscribe to the Calendar instance's "select" event to 
			// update the Button instance's label when the user
			// selects a date.
			oCalendar2.selectEvent.subscribe(function (p_sType, p_aArgs) {

					var aDate2,
							nMonth2,
							nDay2,
							nYear2;

					if (p_aArgs) {
							
							aDate2 = p_aArgs[0][0];

							nMonth2 = aDate2[1];
							nDay2 = aDate2[2];
							nYear2 = aDate2[0];

							oButton2.set("label", (nMonth2 + "/" + nDay2 + "/" + nYear2));


							// Sync the Calendar instance's selected date with the date form fields

							Dom.get("month2").selectedIndex = (nMonth2 - 1);
							Dom.get("day2").selectedIndex = (nDay2 - 1);
							Dom.get("year2").value = nYear2;

					}
					
					oCalendarMenu2.hide();
			
			});


			// Pressing the Esc key will hide the Calendar Menu and send focus back to 
			// its parent Button
			Event.on(oCalendarMenu2.element, "keydown", function (p_oEvent) {
			
					if (Event.getCharCode(p_oEvent) === 27) {
							oCalendarMenu2.hide();
							this.focus();
					}
			
			}, null, this);
			
			
			var focusDay = function () {

					var oCalendarTBody2 = Dom.get("buttoncalendar2").tBodies[0],
							aElements = oCalendarTBody2.getElementsByTagName("a"),
							oAnchor;
					
					if (aElements.length > 0) {
					
							Dom.batch(aElements, function (element) {
							
									if (Dom.hasClass(element.parentNode, "today")) {
											oAnchor = element;
									}
							
							});
							
							
							if (!oAnchor) {
									oAnchor = aElements[0];
							}

							// Focus the anchor element using a timer since Calendar will try 
							// to set focus to its next button by default
							YAHOO.lang.later(0, oAnchor, function () {
									try {
											oAnchor.focus();
									}
									catch(e) {}
							});
					
					}
					
			};


			// Set focus to either the current day, or first day of the month in 
			// the Calendar when it is made visible or the month changes
			oCalendarMenu2.subscribe("show", focusDay2);
			oCalendar2.renderEvent.subscribe(focusDay2, oCalendar2, true);

			// Give the Calendar an initial focus
			focusDay2.call(oCalendar2);


			// Re-align the CalendarMenu to the Button to ensure that it is in the correct
			// position when it is initial made visible
			oCalendarMenu2.align();


			// Unsubscribe from the "click" event so that this code is 
			// only executed once
			this.unsubscribe("click", onButtonClick2);
	
	};


	var oDateFields = Dom.get("datefields2");
			oMonthField2 = Dom.get("month2"),
			oDayField2 = Dom.get("day2"),
			oYearField2 = Dom.get("year2");


	// Hide the form fields used for the date so that they can be replaced by the 
	// calendar button.
	oMonthField2.style.display = "none";
	oDayField2.style.display = "none";
	oYearField2.style.display = "none";


	// Create a Overlay instance to house the Calendar instance
	oCalendarMenu2 = new YAHOO.widget.Overlay("calendarmenu2", { visible: false });


	// Create a Button instance of type "menu"
	var oButton2 = new YAHOO.widget.Button({ 
					type: "menu", 
					id: "calendarpicker2", 
					label: Alfresco.util.message("end.date"),
					menu: oCalendarMenu2, 
					container: "datefields2" });


	oButton2.on("appendTo", function () {
	
			// Create an empty body element for the Overlay instance in order 
			// to reserve space to render the Calendar instance into.
			oCalendarMenu2.setBody("&#32;");
			oCalendarMenu2.body.id = "calendarcontainer2";
	});


	// Add a listener for the "click" event.  This listener will be
	// used to defer the creation the Calendar instance until the 
	// first time the Button's Overlay instance is requested to be displayed
	// by the user.
	oButton2.on("click", onButtonClick2);

});



}());

//]]>

/**** CheckButton and RadioButtons Scripts ****/
    (function () {
		
        var Button = YAHOO.widget.Button;
		YAHOO.util.Event.onContentReady("pt2", function () {
                var oCheckButton1 = new Button("checkbutton1", { label: Alfresco.util.message("export.document.creations") });
                var oCheckButton2 = new Button("checkbutton2", { label: Alfresco.util.message("export.document.modifications") });
                var oCheckButton3 = new Button("checkbutton3", { label: Alfresco.util.message("export.document.reads") });
                var oCheckButton4 = new Button("checkbutton4", { label: Alfresco.util.message("export.connections") });
                var oCheckButton5 = new Button("checkbutton5", { label: Alfresco.util.message("export.user.creations") });
                var oCheckButton6 = new Button("checkbutton6", { label: Alfresco.util.message("export.workflow.creations") }); 
        			
				var onChecked1Change = function (e) {document.getElementById("checkbutton1").checked = e.newValue;};   
				oCheckButton1.addListener("checkedChange", onChecked1Change);
				
				var onChecked2Change = function (e) {document.getElementById("checkbutton2").checked = e.newValue;};   
				oCheckButton2.addListener("checkedChange", onChecked2Change);
				
				var onChecked3Change = function (e) {document.getElementById("checkbutton3").checked = e.newValue;};   
				oCheckButton3.addListener("checkedChange", onChecked3Change);
				
				var onChecked4Change = function (e) {document.getElementById("checkbutton4").checked = e.newValue;};   
				oCheckButton4.addListener("checkedChange", onChecked4Change);
				
				var onChecked5Change = function (e) {document.getElementById("checkbutton5").checked = e.newValue;};   
				oCheckButton5.addListener("checkedChange", onChecked5Change);
				
				var onChecked6Change = function (e) {document.getElementById("checkbutton6").checked = e.newValue;};   
				oCheckButton6.addListener("checkedChange", onChecked6Change);
		});
		 
		var ButtonGroup = YAHOO.widget.ButtonGroup;	
		var onCheckedButtonChange = function (p_oEvent) {
			if(p_oEvent.prevValue) {
				document.getElementById("buttongroup1").value=p_oEvent.newValue.get("value");
			}		
		};

		 YAHOO.util.Event.onContentReady("pt3", function () {
		 
            var oButtonGroup1 = new ButtonGroup("buttongroup1");
			 oButtonGroup1.on("checkedButtonChange", onCheckedButtonChange);
        });

    }());

/**** SCRIPT TO PRINT INFORMATIONS OF FILES ****/
	/**
	* To use Ajax
	*/	 
	function getXhr(){
        var xhr = null; 
		
		if(window.XMLHttpRequest) // Firefox and other
		   xhr = new XMLHttpRequest(); 
		else if(window.ActiveXObject){ // Internet Explorer 
		   try{
				xhr = new ActiveXObject("Msxml2.XMLHTTP");
	       } 
		   catch (e) { xhr = new ActiveXObject("Microsoft.XMLHTTP");  }
		}
		else { // unsupported XMLHttpRequest in the browser
		   alert("Votre navigateur ne supporte pas les objets XMLHTTPRequest..."); 
		   xhr = false; 
		} 
		return xhr;
	}
			
	/**
	* Informations return by WebScript
	*/
	function fileResult(url, id_div, v){
		var xhr = getXhr();
		var res = '';
			
		xhr.onreadystatechange = function(){
			// if server Ok
			if(xhr.readyState == 4 && xhr.status == 200){
				res += xhr.responseText;
				document.getElementById(id_div).innerHTML += res;
				v.src="../images/popup.gif";
			}
		}
		xhr.open("GET", url, true);
		xhr.send(null);
	}	

YAHOO.namespace("fileInfo.container");

/**** Creation of the panelInfo box using Yahoo UI ****/
function initPanelInfo() {
	// Build panelInfo based on markup
	YAHOO.fileInfo.container.panelInfo = new YAHOO.widget.Panel("panelInfo", { xy:[posPanelInfo("x"),270], width:"400px", visible: false } );
	YAHOO.fileInfo.container.panelInfo.render();
}
YAHOO.util.Event.onDOMReady(initPanelInfo);

/**** Init position of panelInfo box ****/
function posPanelInfo(v){
	var pos = 0;

	if (document.body){
		var larg = (document.body.clientWidth);
		var haut = (document.body.clientHeight);
	}
	else{
		var larg = (window.innerWidth);
		var haut = (window.innerHeight);
	}
	
	if (v=="x")
		pos = (larg-370)/2;
	else
		pos = haut/2;
	
	return (pos);
}

/**** Show the pannelInfo box whith information wanted ****/
function printInfo(v, nodeRef) {
	v.src="../images/ajax_anim.gif";
	
	var div = document.getElementById("fileinfo");
	if(div){
		document.getElementById("fileinfo").innerHTML="";	// clear the text
		fileResult("./fileinfo/fileinfo?nodeRef="+nodeRef, "fileinfo", v);	// print new information
	}
	else
		v.src="../images/popup.gif";

	YAHOO.fileInfo.container.panelInfo.show(YAHOO.fileInfo.container.panelInfo, true);	// show the panel
}


/**** Creation of the panelInfoWorkflow box using Yahoo UI ****/
function initPanelInfoWorkflow() {
	// Build panelInfoWorkflow based on markup
	YAHOO.fileInfo.container.panelInfoWorkflow = new YAHOO.widget.Panel("panelInfoWorkflow", { xy:[posPanelInfo("x"),270], width:"450px", visible: false } );
	YAHOO.fileInfo.container.panelInfoWorkflow.render();
}
YAHOO.util.Event.onDOMReady(initPanelInfoWorkflow);

/**** Show the pannelInfoWorkflow box whith information wanted ****/
function printInfoWorkflow(v, workflowId) {
	v.src="../images/ajax_anim.gif";
	
	var div = document.getElementById("workflowinfo");
	if(div){
		document.getElementById("workflowinfo").innerHTML="";	// clear the text
		fileResult("./workflowinfo/workflowinfo?workflowId="+workflowId, "workflowinfo", v);	// print new information
	}
	else
		v.src="../images/popup.gif";

	YAHOO.fileInfo.container.panelInfoWorkflow.show(YAHOO.fileInfo.container.panelInfoWorkflow, true);	// show the panel
}

/**** Drag & Drop Dashboard Script ****/
	YAHOO.util.Event.onDOMReady(function(){
		var zIndex = 0;

		// BEGIN :: Check if cookie is set
		var c1 = YAHOO.util.Cookie.getSub("audit", "c1"); 
		var c2 = YAHOO.util.Cookie.getSub("audit", "c2"); 
		var c3 = YAHOO.util.Cookie.getSub("audit", "c3"); 

		if (c1||c2||c3) {
			var containerRef = [];
			var node;

			// BEGIN :: Removing the nodes
			var	cArray1 = c1.split(",");
			var len = cArray1.length;
			for(var i=0;i<len;i++){
				node = document.getElementById(cArray1[i]);	
				node?containerRef[cArray1[i]] = node.parentNode.removeChild(node):"";
			}
			var	cArray2 = c2.split(",");
			len = cArray2.length;
			for(var i=0;i<len;i++){
				node = document.getElementById(cArray2[i]);	
				node?containerRef[cArray2[i]] = node.parentNode.removeChild(node):"";
			}
			var cArray3 = c3.split(",");
			len = cArray3.length;
			for(var i=0;i<len;i++){
				node = document.getElementById(cArray3[i]);	
				node?containerRef[cArray3[i]] = node.parentNode.removeChild(node):"";
			}
			// END :: Removing the nodes
				
			// BEGIN :: Adding the nodes	
			len = cArray1.length;
			var col = document.getElementById("Column1");
			var tmpCR;
			for(var i=0;i<len;i++){
				tmpCR = containerRef[cArray1[i]]; 
				tmpCR?col.appendChild(tmpCR):"";
			}
			len = cArray2.length;
			var col = document.getElementById("Column2");
			for(var i=0;i<len;i++){
				tmpCR = containerRef[cArray2[i]]; 
				tmpCR?col.appendChild(tmpCR):"";
			}
			len = cArray3.length;
			var col = document.getElementById("Column3");
			for(var i=0;i<len;i++){
				tmpCR = containerRef[cArray3[i]]; 
				tmpCR?col.appendChild(tmpCR):"";
			}
			// END :: Adding the nodes	
		} 
		// END :: Check if cookie is set

		document.body.style.display="block";

		// BEGIN :: Non-draggable targets
		var col1Target = new YAHOO.util.DDTarget("Column1", "Group1");
		var col2Target = new YAHOO.util.DDTarget("Column2", "Group1");
		var col3Target = new YAHOO.util.DDTarget("Column3", "Group1");
		// END :: Non-draggable targets

		// BEGIN :: Objects to drag
		var rec1 = document.getElementById("page_x002e_topread_x002e_home");
		var rec2 = document.getElementById("page_x002e_workflow_x002e_home");
		var rec3 = document.getElementById("page_x002e_connusr_x002e_home");
		var rec4 = document.getElementById("page_x002e_lastmod_x002e_home");
		var rec5 = document.getElementById("page_x002e_lastup_x002e_home");
		var rec6 = document.getElementById("page_x002e_lastusr_x002e_home");
		var rec7 = document.getElementById("page_x002e_lock_x002e_home");
		var rec8 = document.getElementById("page_x002e_nolog_x002e_home");
		var rec9 = document.getElementById("page_x002e_topmodif_x002e_home");
		
		var rec1Drag = new YAHOO.util.DDProxy(rec1, "Group1");
		rec1Drag.setHandleElId("Ctrl_top_read");
		
		var rec2Drag = new YAHOO.util.DDProxy(rec2, "Group1");
		rec2Drag.setHandleElId("Ctrl_wf_list");
		
		var rec3Drag = new YAHOO.util.DDProxy(rec3, "Group1");
		rec3Drag.setHandleElId("Ctrl_conn_user");
		
		var rec4Drag = new YAHOO.util.DDProxy(rec4, "Group1");
		rec4Drag.setHandleElId("Ctrl_last_mod");
		
		var rec5Drag = new YAHOO.util.DDProxy(rec5, "Group1");
		rec5Drag.setHandleElId("Ctrl_last_up");
		
		var rec6Drag = new YAHOO.util.DDProxy(rec6, "Group1");
		rec6Drag.setHandleElId("Ctrl_last_usr");

		var rec7Drag = new YAHOO.util.DDProxy(rec7, "Group1");
		rec7Drag.setHandleElId("Ctrl_lock");
		
		var rec8Drag = new YAHOO.util.DDProxy(rec8, "Group1");
		rec8Drag.setHandleElId("Ctrl_non_log");
		
		var rec9Drag = new YAHOO.util.DDProxy(rec9, "Group1");
		rec9Drag.setHandleElId("Ctrl_top_modif");
		// END :: Objects to drag
		
		// BEGIN :: Event handlers
		var marker, container, oriContainer;
		var lastRectNode = [];
		marker = document.createElement("div");	

		rec1Drag.startDrag = rec2Drag.startDrag = rec3Drag.startDrag = rec4Drag.startDrag = rec5Drag.startDrag = rec6Drag.startDrag = rec7Drag.startDrag = rec8Drag.startDrag= rec9Drag.startDrag = function(x, y) {
			var dragEl = this.getDragEl(); 
			var el = this.getEl();
			oriContainer = container = el.parentNode;
			el.style.display = "none";
			dragEl.style.zIndex = ++zIndex;
			dragEl.innerHTML = el.innerHTML;
			dragEl.style.textAlign = "left";
			dragEl.style.color = "#ebebeb";
			dragEl.style.backgroundColor = "#fff";
			marker.style.display = "none";
			marker.style.height = YAHOO.util.Dom.getStyle(dragEl, "height");	
			marker.style.width = YAHOO.util.Dom.getStyle(dragEl, "width");
			marker.style.margin = "5px"; 
			marker.style.marginBottom = "20px"; 
			marker.style.border = "2px dashed #7e7e7e";
			marker.style.display= "block";
			container.insertBefore(marker, el);
		}
		col1Target.onDragEnter = col2Target.onDragEnter = col3Target.onDragEnter  = rec1Drag.onDragEnter = rec2Drag.onDragEnter = rec3Drag.onDragEnter = rec4Drag.onDragEnter = rec5Drag.onDragEnter = rec6Drag.onDragEnter = rec7Drag.onDragEnter = rec8Drag.onDragEnter = rec9Drag.onDragEnter = function(e, id) {
			var el = document.getElementById(id);
			if (id.substr(0, 6)	=== "Column") {
				el.appendChild(marker);
			} else {
				container = el.parentNode;
				container.insertBefore(marker, el);
			}
		}
		rec1Drag.onDragOut = rec2Drag.onDragOut = rec3Drag.onDragOut = rec4Drag.onDragOut = rec5Drag.onDragOut = rec6Drag.onDragOut = rec7Drag.onDragOut = rec8Drag.onDragOut = rec9Drag.onDragOut = function(e, id) {
			var el = document.getElementById(id);
			lastRectNode[container.id] = getLastNode(container.lastChild);

			if (lastRectNode[container.id] && el.id === lastRectNode[container.id].id) {
				container.appendChild(marker);
			}	
		}
		rec1Drag.endDrag = rec2Drag.endDrag = rec3Drag.endDrag = rec4Drag.endDrag = rec5Drag.endDrag = rec6Drag.endDrag = rec7Drag.endDrag = rec8Drag.endDrag = rec9Drag.endDrag= function(e, id) {
			var el = this.getEl(); 

			try {
				marker = container.replaceChild(el, marker);
			} catch(err) {
				marker = marker.parentNode.replaceChild(el, marker);
			}	
			el.style.display = "block";
		}

		var setCookies = function() {
			// BEGIN :: Calculate cookie expiration to 14 days from today
			var date = new Date();
			date.setTime(date.getTime()+(14*24*60*60*1000)); 
			// END :: Calculate cookie expiration to 14 days from today
			var getNode = function(node) {
				return (node.id==="page_x002e_topread_x002e_home"||node.id==="page_x002e_workflow_x002e_home"||node.id==="page_x002e_connusr_x002e_home"||node.id==="page_x002e_lastmod_x002e_home"||node.id==="page_x002e_lastup_x002e_home"||node.id==="page_x002e_lastusr_x002e_home"||node.id==="page_x002e_lock_x002e_home"||node.id==="page_x002e_nolog_x002e_home"||node.id==="page_x002e_topmodif_x002e_home");
			}
			var createString = function(colId) {
				var nodes = YAHOO.util.Dom.getChildrenBy(document.getElementById(colId), getNode);
				var list = [];
				var l = nodes.length;
				for(var i=0;i<l;i++) {
					list[i] = nodes[i].id;
				}
				return list.toString();
			}
			YAHOO.util.Cookie.setSub("audit", "c1", createString("Column1"), {expires: date}); 
			YAHOO.util.Cookie.setSub("audit", "c2", createString("Column2"), {expires: date}); 
			YAHOO.util.Cookie.setSub("audit", "c3", createString("Column3"), {expires: date}); 
		}
		YAHOO.util.Event.on(window, "unload", setCookies); 	
		// END :: Event handlers
		
		// BEGIN :: Helper methods
		var getLastNode = function(lastChild) {
				if(lastChild) {
					var id = lastChild.id;
					if (id && id.substring(0, 11) === "page_x002e_") {
						return lastChild;
					} 
					return getLastNode(lastChild.previousSibling);
				}
		}
		var isEmpty = function(el) {
				var test = function(el) { 
					return ((el && el.id) ? el.id.substr(0, 11) == "page_x002e_" : false);
				} 
				var kids = YAHOO.util.Dom.getChildrenBy(el, test);
				return (kids.length == 0 ? true : false);
		}
		// END :: Helper methods
	});


/**** Drag & Drop Monitoring Script ****/
	YAHOO.util.Event.onDOMReady(function(){
		var zIndex = 0;

		// BEGIN :: Check if cookie is set
		var c1 = YAHOO.util.Cookie.getSub("audit_monit", "c1"); 
		var c2 = YAHOO.util.Cookie.getSub("audit_monit", "c2"); 
		var c3 = YAHOO.util.Cookie.getSub("audit_monit", "c3"); 

		if (c1||c2||c3) {
			var containerRef = [];
			var node;

			// BEGIN :: Removing the nodes
			var	cArray1 = c1.split(",");
			var len = cArray1.length;
			for(var i=0;i<len;i++){
				node = document.getElementById(cArray1[i]);	
				node?containerRef[cArray1[i]] = node.parentNode.removeChild(node):"";
			}
			var	cArray2 = c2.split(",");
			len = cArray2.length;
			for(var i=0;i<len;i++){
				node = document.getElementById(cArray2[i]);	
				node?containerRef[cArray2[i]] = node.parentNode.removeChild(node):"";
			}
			var cArray3 = c3.split(",");
			len = cArray3.length;
			for(var i=0;i<len;i++){
				node = document.getElementById(cArray3[i]);	
				node?containerRef[cArray3[i]] = node.parentNode.removeChild(node):"";
			}
			// END :: Removing the nodes
				
			// BEGIN :: Adding the nodes	
			len = cArray1.length;
			var col = document.getElementById("MonitColumn1");
			var tmpCR;
			for(var i=0;i<len;i++){
				tmpCR = containerRef[cArray1[i]]; 
				tmpCR?col.appendChild(tmpCR):"";
			}
			len = cArray2.length;
			var col = document.getElementById("MonitColumn2");
			for(var i=0;i<len;i++){
				tmpCR = containerRef[cArray2[i]]; 
				tmpCR?col.appendChild(tmpCR):"";
			}
			len = cArray3.length;
			var col = document.getElementById("MonitColumn3");
			for(var i=0;i<len;i++){
				tmpCR = containerRef[cArray3[i]]; 
				tmpCR?col.appendChild(tmpCR):"";
			}
			// END :: Adding the nodes	
		} 
		// END :: Check if cookie is set

		document.body.style.display="block";

		// BEGIN :: Non-draggable targets
		var col1Target = new YAHOO.util.DDTarget("MonitColumn1", "Group2");
		var col2Target = new YAHOO.util.DDTarget("MonitColumn2", "Group2");
		var col3Target = new YAHOO.util.DDTarget("MonitColumn3", "Group2");
		// END :: Non-draggable targets

		// BEGIN :: Objects to drag
		var rec1 = document.getElementById("MonitRec1");
		var rec2 = document.getElementById("MonitRec2");
		var rec3 = document.getElementById("MonitRec3");
		var rec5 = document.getElementById("MonitRec5");
		var rec6 = document.getElementById("MonitRec6");
		var rec7 = document.getElementById("MonitRec7");
		
		var rec1Drag = new YAHOO.util.DDProxy(rec1, "Group2");
		rec1Drag.setHandleElId("Ctrl_alf_info");
		
		var rec2Drag = new YAHOO.util.DDProxy(rec2, "Group2");
		rec2Drag.setHandleElId("Ctrl_runtime");
		
		var rec3Drag = new YAHOO.util.DDProxy(rec3, "Group2");
		rec3Drag.setHandleElId("Ctrl_contentStores");
		
		var rec5Drag = new YAHOO.util.DDProxy(rec5, "Group2");
		rec5Drag.setHandleElId("Ctrl_luceneindexes");
		
		var rec6Drag = new YAHOO.util.DDProxy(rec6, "Group2");
		rec6Drag.setHandleElId("Ctrl_dbinfo");
		
		var rec7Drag = new YAHOO.util.DDProxy(rec7, "Group2");
		rec7Drag.setHandleElId("Ctrl_access");
		
		// END :: Objects to drag
		
		// BEGIN :: Event handlers
		var marker, container, oriContainer;
		var lastRectNode = [];
		marker = document.createElement("div");	

		rec1Drag.startDrag = rec2Drag.startDrag = rec3Drag.startDrag = rec5Drag.startDrag = rec6Drag.startDrag= rec7Drag.startDrag= function(x, y) {
			var dragEl = this.getDragEl(); 
			var el = this.getEl();
			oriContainer = container = el.parentNode;
			el.style.display = "none";
			dragEl.style.zIndex = ++zIndex;
			dragEl.innerHTML = el.innerHTML;
			dragEl.style.color = "#ebebeb";
			dragEl.style.backgroundColor = "#fff";
			marker.style.display = "none";
			marker.style.height = YAHOO.util.Dom.getStyle(dragEl, "height");	
			marker.style.width = YAHOO.util.Dom.getStyle(dragEl, "width");
			marker.style.margin = "5px"; 
			marker.style.marginBottom = "20px"; 
			marker.style.border = "2px dashed #7e7e7e";
			marker.style.display= "block";
			container.insertBefore(marker, el);
		}
		col1Target.onDragEnter = col2Target.onDragEnter = col3Target.onDragEnter  = rec1Drag.onDragEnter = rec2Drag.onDragEnter = rec3Drag.onDragEnter = rec5Drag.onDragEnter = rec6Drag.onDragEnter = rec7Drag.onDragEnter = function(e, id) {
			var el = document.getElementById(id);
			if (id.substr(0, 11)	=== "MonitColumn") {
				el.appendChild(marker);
			} else {
				container = el.parentNode;
				container.insertBefore(marker, el);
			}
		}
		rec1Drag.onDragOut = rec2Drag.onDragOut = rec3Drag.onDragOut = rec5Drag.onDragOut = rec6Drag.onDragOut = rec7Drag.onDragOut = function(e, id) {
			var el = document.getElementById(id);
			lastRectNode[container.id] = getLastNode(container.lastChild);

			if (lastRectNode[container.id] && el.id === lastRectNode[container.id].id) {
				container.appendChild(marker);
			}	
		}
		rec1Drag.endDrag = rec2Drag.endDrag = rec3Drag.endDrag = rec5Drag.endDrag = rec6Drag.endDrag = rec7Drag.endDrag = function(e, id) {
			var el = this.getEl(); 

			try {
				marker = container.replaceChild(el, marker);
			} catch(err) {
				marker = marker.parentNode.replaceChild(el, marker);
			}	
			el.style.display = "block";
		}

		var setCookies = function() {
			// BEGIN :: Calculate cookie expiration to 14 days from today
			var date = new Date();
			date.setTime(date.getTime()+(14*24*60*60*1000)); 
			// END :: Calculate cookie expiration to 14 days from today
			var getNode = function(node) {
				return (node.id==="MonitRec1"||node.id==="MonitRec2"||node.id==="MonitRec3"||node.id==="MonitRec5"||node.id==="MonitRec6"||node.id==="MonitRec7");
			}
			var createString = function(colId) {
				var nodes = YAHOO.util.Dom.getChildrenBy(document.getElementById(colId), getNode);
				var list = [];
				var l = nodes.length;
				for(var i=0;i<l;i++) {
					list[i] = nodes[i].id;
				}
				return list.toString();
			}
			YAHOO.util.Cookie.setSub("audit_monit", "c1", createString("MonitColumn1"), {expires: date}); 
			YAHOO.util.Cookie.setSub("audit_monit", "c2", createString("MonitColumn2"), {expires: date}); 
			YAHOO.util.Cookie.setSub("audit_monit", "c3", createString("MonitColumn3"), {expires: date}); 
		}
		YAHOO.util.Event.on(window, "unload", setCookies); 	
		// END :: Event handlers
		
		// BEGIN :: Helper methods
		var getLastNode = function(lastChild) {
				if(lastChild) {
					var id = lastChild.id;
					if (id && id.substring(0, 8) === "MonitRec") {
						return lastChild;
					} 
					return getLastNode(lastChild.previousSibling);
				}
		}
		var isEmpty = function(el) {
				var test = function(el) { 
					return ((el && el.id) ? el.id.substr(0, 8) == "MonitRec" : false);
				} 
				var kids = YAHOO.util.Dom.getChildrenBy(el, test);
				return (kids.length == 0 ? true : false);
		}
		// END :: Helper methods
	});