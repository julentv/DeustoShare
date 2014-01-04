/*
	Prologue 1.1 by HTML5 UP
	html5up.net | @n33co
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
 */

/*********************************************************************************/
/* Settings                                                                      */
/** ****************************************************************************** */

var prologue_settings = {

	// skelJS (probably don't need to change anything here unless you know what
	// you're doing)
	skelJS : {
		prefix : '/css/style',
		resetCSS : true,
		boxModel : 'border',
		useOrientation : true,
		breakpoints : {
			'widest' : {
				range : '1881-',
				hasStyleSheet : false,
				containers : 1400,
				grid : {
					gutters : 70
				}
			},
			'wide' : {
				range : '961-1880',
				containers : 1200,
				grid : {
					gutters : 40
				}
			},
			'normal' : {
				range : '961-1620',
				containers : 960,
				grid : {
					gutters : 40
				}
			},
			'narrow' : {
				range : '961-1320',
				containers : 'fluid',
				grid : {
					gutters : 30
				}
			},
			'narrower' : {
				range : '-960',
				containers : 'fluid',
				grid : {
					gutters : 30
				}
			},
			'mobile' : {
				range : '-640',
				containers : 'fluid',
				lockViewport : true,
				grid : {
					gutters : 30,
					collapse : true
				}
			}
		}
	},

	// skelJS Plugins (ditto; don't change unless you know what you're doing)
	skelJSPlugins : {
		panels : {
			panels : {
				sidePanel : {
					breakpoints : 'narrower',
					position : 'left',
					size : 240,
					html : '<div data-action="moveElement" data-args="header"></div></div>'
				}
			},
			overlays : {
				sidePanelToggle : {
					breakpoints : 'narrower',
					position : 'top-left',
					width : '3.5em',
					height : '2.25em',
					html : '<div data-action="togglePanel" data-args="sidePanel" class="toggle"></div>'
				}
			}
		}
	}

};

/** ****************************************************************************** */
/* Don't modify beyond this point unless you know what you're doing! */
/** ****************************************************************************** */

// Initialize skelJS
skel.init(prologue_settings.skelJS, prologue_settings.skelJSPlugins);

// jQuery functions

// formerize
jQuery.fn.n33_formerize = function() {
	var _fakes = new Array(), _form = jQuery(this);
	_form.find('input[type=text],textarea').each(function() {
		var e = jQuery(this);
		if (e.val() == '' || e.val() == e.attr('placeholder')) {
			e.addClass('formerize-placeholder');
			e.val(e.attr('placeholder'));
		}
	}).blur(function() {
		var e = jQuery(this);
		if (e.attr('name').match(/_fakeformerizefield$/))
			return;
		if (e.val() == '') {
			e.addClass('formerize-placeholder');
			e.val(e.attr('placeholder'));
		}
	}).focus(function() {
		var e = jQuery(this);
		if (e.attr('name').match(/_fakeformerizefield$/))
			return;
		if (e.val() == e.attr('placeholder')) {
			e.removeClass('formerize-placeholder');
			e.val('');
		}
	});
	_form.find('input[type=password]').each(
			function() {
				var e = jQuery(this);
				var x = jQuery(jQuery('<div>').append(e.clone()).remove()
						.html().replace(/type="password"/i, 'type="text"')
						.replace(/type=password/i, 'type=text'));
				if (e.attr('id') != '')
					x.attr('id', e.attr('id') + '_fakeformerizefield');
				if (e.attr('name') != '')
					x.attr('name', e.attr('name') + '_fakeformerizefield');
				x.addClass('formerize-placeholder').val(x.attr('placeholder'))
						.insertAfter(e);
				if (e.val() == '')
					e.hide();
				else
					x.hide();
				e.blur(function(event) {
					event.preventDefault();
					var e = jQuery(this);
					var x = e.parent().find(
							'input[name=' + e.attr('name')
									+ '_fakeformerizefield]');
					if (e.val() == '') {
						e.hide();
						x.show();
					}
				});
				x.focus(function(event) {
					event.preventDefault();
					var x = jQuery(this);
					var e = x.parent().find(
							'input[name='
									+ x.attr('name').replace(
											'_fakeformerizefield', '') + ']');
					x.hide();
					e.show().focus();
				});
				x.keypress(function(event) {
					event.preventDefault();
					x.val('');
				});
			});
	_form.submit(
			function() {
				jQuery(this).find(
						'input[type=text],input[type=password],textarea').each(
						function(event) {
							var e = jQuery(this);
							if (e.attr('name').match(/_fakeformerizefield$/))
								e.attr('name', '');
							if (e.val() == e.attr('placeholder')) {
								e.removeClass('formerize-placeholder');
								e.val('');
							}
						});
			}).bind(
			"reset",
			function(event) {
				event.preventDefault();
				jQuery(this).find('select').val(jQuery('option:first').val());
				jQuery(this).find('input,textarea').each(
						function() {
							var e = jQuery(this);
							var x;
							e.removeClass('formerize-placeholder');
							switch (this.type) {
							case 'submit':
							case 'reset':
								break;
							case 'password':
								e.val(e.attr('defaultValue'));
								x = e.parent().find(
										'input[name=' + e.attr('name')
												+ '_fakeformerizefield]');
								if (e.val() == '') {
									e.hide();
									x.show();
								} else {
									e.show();
									x.hide();
								}
								break;
							case 'checkbox':
							case 'radio':
								e.attr('checked', e.attr('defaultValue'));
								break;
							case 'text':
							case 'textarea':
								e.val(e.attr('defaultValue'));
								if (e.val() == '') {
									e.addClass('formerize-placeholder');
									e.val(e.attr('placeholder'));
								}
								break;
							default:
								e.val(e.attr('defaultValue'));
								break;
							}
						});
				window.setTimeout(function() {
					for (x in _fakes)
						_fakes[x].trigger('formerize_sync');
				}, 10);
			});
	return _form;
};

/* My methods */

function setContent(url) {
	var loadingSection = '<div class="loading-cont"><section id="loading" class="five"><div class="container"><div class="loading"><div class="bulat">'
			+ '<div id="dalbulat"><span>L</span> <span>O</span><span>A</span><span>D</span><span>I</span><span>N</span><span>G</span></div>'
			+ '<div class="luarbulat"></div></div></div></div></div></section></div>';
	changeSelectedMenu(url);
	$("#main").html(loadingSection);
	$.ajax({
		url : url,
		context : document.body,
		success : function(data, status, settings) {
			$("#main").html(data);
		}
	});
}
function setContentForm(url, formId) {
	var loadingSection = '<div class="loading-cont"><section id="loading" class="five"><div class="container"><div class="loading"><div class="bulat">'
			+ '<div id="dalbulat"><span>L</span> <span>O</span><span>A</span><span>D</span><span>I</span><span>N</span><span>G</span></div>'
			+ '<div class="luarbulat"></div></div></div></div></div></section></div>';
	changeSelectedMenu(url);
	var dataForm = $('#' + formId).serialize();
	$("#main").html(loadingSection);
	$.ajax({
		type : "POST",
		url : url,
		data : dataForm,
		context : document.body,
		success : function(data, status, settings) {
			$("#main").html(data);
		}
	});
}
var currentSelectedMenuOption = "home";
function changeSelectedMenu(name) {
	if(name.indexOf("start/request")!=-1){
		name="start/my_requests";
	}
	if (name == "start/home" || name == "start/my_resources"
			|| name == "start/my_requests" || name == "start/received_requests"
			|| name == "start/search_resources_page") {
		name = name.replace("start/", "");
		$("#" + currentSelectedMenuOption).removeClass("active");
		$("#" + name).addClass("active");
		currentSelectedMenuOption = name;
	}
}
function displayResourcesSearch(url, formId, displayDiv) {
	var loadingSection = '<div class="loading-cont"><section id="loading" class="five"><div class="container"><div class="loading"><div class="bulat">'
			+ '<div id="dalbulat"><span>L</span> <span>O</span><span>A</span><span>D</span><span>I</span><span>N</span><span>G</span></div>'
			+ '<div class="luarbulat"></div></div></div></div></div></section></div>';
	changeSelectedMenu(url);
	var dataForm = $('#' + formId).serialize();
	$("#" + displayDiv).html(loadingSection);
	$.ajax({
		type : "POST",
		url : url,
		data : dataForm,
		context : document.body,
		success : function(data, status, settings) {
			$("#" + displayDiv).html(data);
		}
	});
}
function changeSelectedMenuFilter(url,select){
	url=url+"?filter="+select.options[select.selectedIndex].value;
	setContent(url);
}