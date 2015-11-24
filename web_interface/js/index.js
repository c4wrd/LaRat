var globals = globals || {};
globals.initialized = false;
globals.buttons = {}
globals.forms = {}
globals.divs = {}
globals.clients = []



var setupGlobals = function() {
	globals.buttons.wake_screen = $("#btn-screen-on");
	globals.buttons.toast_short = $("#btn-short-toast")
	globals.buttons.toast_long = $("#btn-long-toast")
	globals.buttons.open_gl = $("#btn-open-gl");
	globals.buttons.screen_crack = $("#btn-screen-crack");
	globals.buttons.pong = $("#btn-pong")
	
	globals.forms.toast_test = $("#toast_message_input");
	globals.forms.thread_id = $("#thread_input")

	globals.divs.client_container = $("#client-container");
	globals.divs.messages = $("#notification-center");
	globals.initialized = true;
}

var initButtonHandlers = function() {
	globals.buttons.wake_screen.click( function() {
		globals.clients.forEach(function(client) {
			$.post("command.php", {"command": "sendCommand", "fn": "ScreenOn", "client_id": client});
		});
	});
	
	
}

$(document).ready(function() {
	setupGlobals();	
	if (globals.initialized != true) {
		alert("We have failed to setup some global variables :(");
	}
});