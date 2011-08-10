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

<div id="export">
	<form id="theform" action="" enctype="multipart/form-data" method="post">
	
<table class="tab_gr" >
	<tbody>
		<tr>
			<td class="table_head_top_left" ></td>
			<td class="table_head_top_center" ></td>
			<td class="table_head_top_right" ></td>
		</tr>
		<tr>
			<td class="table_body_left" ></td>
			<td class="table_body_center" >
	  <fieldset id="pt1">
		<legend><span>Step</span> 1. <span>: Date details</span></legend>
		<h3>
		  ${msg("date")}
		</h3>
		<div class="field" id="datefields">
		  <select id="month" name="month">
			<option value="01">01</option>
			<option value="02">02</option>
			<option value="03">03</option>
			<option value="04">04</option>
			<option value="05">05</option>
			<option value="06">06</option>
			<option value="07">07</option>
			<option value="08">08</option>
			<option value="09">09</option>
			<option value="10">10</option>
			<option value="11">11</option>
			<option value="12">12</option>
		  </select> 
		  <select id="day" name="day">
			<option value="01">01</option>
			<option value="02">02</option>
			<option value="03">03</option>
			<option value="04">04</option>
			<option value="05">05</option>
			<option value="06">06</option>
			<option value="07">07</option>
			<option value="08">08</option>
			<option value="09">09</option>
			<option value="10">10</option>
			<option value="11">11</option>
			<option value="12">12</option>
			<option value="13">13</option>
			<option value="14">14</option>
			<option value="15">15</option>
			<option value="16">16</option>
			<option value="17">17</option>
			<option value="18">18</option>
			<option value="19">19</option>
			<option value="20">20</option>
			<option value="21">21</option>
			<option value="22">22</option>
			<option value="23">23</option>
			<option value="24">24</option>
			<option value="25">25</option>
			<option value="26">26</option>
			<option value="27">27</option>
			<option value="28">28</option>
			<option value="29">29</option>
			<option value="30">30</option>
			<option value="31">31</option>
		  </select> <input type="text" id="year" name="year" value="">
		</div>
	  <div class="field" id="datefields2">
		  <select id="month2" name="month2">
			<option value="01">01</option>
			<option value="02">02</option>
			<option value="03">03</option>
			<option value="04">04</option>
			<option value="05">05</option>
			<option value="06">06</option>
			<option value="07">07</option>
			<option value="08">08</option>
			<option value="09">09</option>
			<option value="10">10</option>
			<option value="11">11</option>
			<option value="12">12</option>
		  </select> 
		  <select id="day2" name="day2">
			<option value="01">01</option>
			<option value="02">02</option>
			<option value="03">03</option>
			<option value="04">04</option>
			<option value="05">05</option>
			<option value="06">06</option>
			<option value="07">07</option>
			<option value="08">08</option>
			<option value="09">09</option>
			<option value="10">10</option>
			<option value="11">11</option>
			<option value="12">12</option>
			<option value="13">13</option>
			<option value="14">14</option>
			<option value="15">15</option>
			<option value="16">16</option>
			<option value="17">17</option>
			<option value="18">18</option>
			<option value="19">19</option>
			<option value="20">20</option>
			<option value="21">21</option>
			<option value="22">22</option>
			<option value="23">23</option>
			<option value="24">24</option>
			<option value="25">25</option>
			<option value="26">26</option>
			<option value="27">27</option>
			<option value="28">28</option>
			<option value="29">29</option>
			<option value="30">30</option>
			<option value="31">31</option>
		  </select> <input type="text" id="year2" name="year2" value="">
		</div>
	  </fieldset>
	  <fieldset id="pt2">
		<legend><span>Step</span> 2. <span>: Actions</span></legend>
		<h3>
		  ${msg("export")}
		</h3>
		<input id="checkbutton1" type="checkbox" name="docs_creation" value="docs_creation">
		<input id="checkbutton2" type="checkbox" name="docs_mod" value="docs_mod">
		<input id="checkbutton3" type="checkbox" name="docs_read" value="docs_read">
		<input id="checkbutton4" type="checkbox" name="conn" value="conn">
		<input id="checkbutton5" type="checkbox" name="createduser" value="createduser">
		<input id="checkbutton6" type="checkbox" name="createdwork" value="createdwork">
	  </fieldset>
	  <fieldset id="pt3">
		<legend><span>Step</span> 3. <span>: Format details</span></legend>
		<h3>
			${msg("output")}
		</h3>
		<div id="buttongroup1" class="yui-buttongroup">
			<input id="radio1" type="radio" name="radiofield1" value="json" checked>
			<input id="radio2" type="radio" name="radiofield1" value="xml">
			<input id="radio3" type="radio" name="radiofield1" value="csv">
		</div>

	  </fieldset>
	  <fieldset id="pt4">
		<legend><span>Step</span> 4. <span>: Click</span></legend>
			<h3>
				${msg("click")}
			</h3>
		<div id="buttoncontainer"></div>
	  </fieldset>
	  		</td>
			<td class="table_body_right" ></td>
		</tr>

		<tr>
			<td class="table_foot_left" ></td>
			<td class="table_foot_center" ></td>
			<td class="table_foot_right" ></td>
		</tr>
	</tbody>
</table>
	  
	</form>	
</div>
