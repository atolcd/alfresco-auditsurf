/*
yodeler YUI Addons - widget.ScrollTabView
Copyright 2008 Reid Burke <me@reidburke.com>
Code licensed under the BSD License: http://internal.reidburke.com/yui-addons/license.txt
*/

/**
 * An extension of TabView that transitions between tabs using a Scroll Animation.
 * 
 * @namespace YAHOO.yodeler.widget
 * @module ScrollTabView
 * @requires yahoo, event, dom, anim, tabview
 * @author Reid Burke <me@reidburke.com>
 * @version 0.1
 */

YAHOO.namespace('yodeler.widget');

(function() {

	/**
	 * @constructor
	 * @extends YAHOO.widget.TabView
	 * @param {HTMLElement | String | Object} el (optional) The html 
	 * element that represents the ScrollTabView, or the attribute 
	 * object to use. 
	 * An element will be created if none provided.
	 * @param {Object} attr (optional) A key map of ScrollTabView's 
	 * initial attributes. Ignored if first arg is attributes object.
	 */	
	YAHOO.yodeler.widget.ScrollTabView = function (el, attr) {
		YAHOO.yodeler.widget.ScrollTabView.superclass.constructor.call(this, el, attr); 
	}

	YAHOO.lang.extend(YAHOO.yodeler.widget.ScrollTabView, YAHOO.widget.TabView);

	var proto = YAHOO.yodeler.widget.ScrollTabView.prototype,
		Dom = YAHOO.util.Dom;

	/**
	 * Overrides default contentTransition with a transition
	 * that animates between Tab views.
	 *
	 * @method contentTransition
	 * @param {YAHOO.widget.Tab} newTab
	 * @param {YAHOO.widget.Tab} oldTab
	 * @return void
	 */
	proto.contentTransition = function(newTab, oldTab) {
		var px, dims, ani;
		switch (this.get('direction')) {
			case 'horizontal':
				px = this.get('width') * this.getTabIndex(newTab);
				dims = [px, 0];
			break;
			case 'vertical':
				px = this.get('height') * this.getTabIndex(newTab);
				dims = [0, px]
			break;
		}
		newTab.set('contentVisible', true);
		ani = new YAHOO.util.Scroll(this._contentParent,
			{ scroll: { to: dims } },
			this.get('duration'),
			this.get('easing')
		);
		ani.onComplete.subscribe(function() {
			oldTab.set('contentVisible', false);
			// Keep content visible for effect during transitions
			oldTab.get('contentEl').style.display = 'block';
		});
		ani.animate();
	}

	/**
	 * Adds a Tab to the ScrollTabView.  
	 * If no index is specified, the tab is added to the end of the tab list.
	 * 
	 * @method addTab
	 * @param {YAHOO.widget.Tab} tab A Tab instance to add.
	 * @param {Integer} index The position to add the tab. 
	 * @return void
	 */
	proto.addTab = function(tab, index) {
		YAHOO.yodeler.widget.ScrollTabView.superclass.addTab.call(this, tab, index);
		_initTabStyle.call(this);
	}

	/**
	 * Removes the specified Tab from the ScrollTabView.
	 *
	 * @method removeTab
	 * @param {YAHOO.widget.Tab} item The Tab instance to be removed.
	 * @return void
	 */
	proto.removeTab = function(tab) {
		YAHOO.yodeler.widget.ScrollTabView.superclass.removeTab.call(this, tab);
		_initTabStyle.call(this);
	}

	/**
	 * setAttributeConfigs ScrollTabView specific properties.
	 *
	 * @method initAttributes
	 * @param {Object} attr Hash of initial attributes
	 */
	proto.initAttributes = function(attr) {

		YAHOO.yodeler.widget.ScrollTabView.superclass.initAttributes.call(this, attr);

		this.setAttributeConfig('width', {
			value: attr.width || false,
			method: _initTabStyle.call(this),
			validator: YAHOO.lang.isNumber
		});
		this.setAttributeConfig('height', {
			value: attr.height || false,
			method: _initTabStyle.call(this),
			validator: YAHOO.lang.isNumber
		});
		this.setAttributeConfig('direction', {
			value: attr.direction || 'horizontal',
			method: _initTabStyle.call(this)
		});
		this.setAttributeConfig('easing', {
			value: attr.easing || YAHOO.util.Easing.easeBothStrong,
			validator: YAHOO.lang.isFunction
		});
		this.setAttributeConfig('duration', {
			value: attr.duration || 1,
			validator: YAHOO.lang.isNumber
		});

		// Override TabView's refusal to contentTransition
		// when the tab is already set to contentVisible
		this.addListener('activeTabChange', function(ev) {
			var activeTab = this.get('activeTab');
			if (ev.newValue && !(activeTab && ev.newValue != activeTab)) {
				this.contentTransition(ev.newValue, activeTab);
			}
		});

		// Setup element styles
		_initTabStyle.call(this);
	}

	var _initTabStyle = function() {

		var width = this.get('width');
		var height = this.get('height');
		var direction = this.get('direction');

		if (!width || !height || !direction) return false; // wait until all Attributes are set

		Dom.setStyle(this._contentParent, 'overflow', 'hidden');
		Dom.setStyle(this._contentParent, 'position', 'relative');
		Dom.setStyle(this._contentParent, 'width', width + 'px');
		Dom.setStyle(this._contentParent, 'height', height + 'px');

		var tabs = this.get('tabs');

		for (var i = 0, length = tabs.length; i < length; ++i) {

			var contentElement = tabs[i].get('contentEl');

			Dom.setStyle(contentElement, 'position', 'absolute');
			
			switch (direction) {
				case 'horizontal':
					Dom.setStyle(contentElement, 'top', '0');
					Dom.setStyle(contentElement, 'left', (width * i) + 'px');
					Dom.setStyle(contentElement, 'width', width + 'px');
				break;
				case 'vertical':
					Dom.setStyle(contentElement, 'left', '0');
					Dom.setStyle(contentElement, 'top', (height * i) + 'px');
					Dom.setStyle(contentElement, 'height', height + 'px');
				break;
			}

			// Keep content visible for effect during transitions
			contentElement.style.display = 'block';

		}

	}

})();
