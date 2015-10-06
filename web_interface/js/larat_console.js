var greeting = "Welcome to the LaRat Interactive Console! \n\
                  Commands:\n\
                      get             - lists availiable clients\n\
                      add [clientId]  - adds client to current client array\n\
                      rm [clientId]   - removes client from client pool\n\
                      ssh [clientId]  - creates remote shell on client (client pool not supported)\n\
                      toast [message] - toasts message on clients in current client array\n\
                      list            - lists clients in current client array\n\
                      info [clientId] - lists the info of a given client\n\
                      screenon [clientId] - turns on the specified clients screen\n";

String.prototype.strip = function(char) {
    return this.replace(new RegExp("^" + char + "*"), '').
        replace(new RegExp(char + "*$"), '');
}

$.extend_if_has = function(desc, source, array) {
    for (var i=array.length;i--;) {
        if (typeof source[array[i]] != 'undefined') {
            desc[array[i]] = source[array[i]];
        }
    }
    return desc;
};


(function($) {
    $.fn.tilda = function(eval, options) {
        if ($('body').data('tilda')) {
            return $('body').data('tilda').terminal;
        }
        this.addClass('tilda');
        options = options || {};
        eval = eval || function(command, term) {
            term.echo("you don't set eval for tilda");
        };

        var settings = {
            prompt: 'tilda> ',
            name: 'tilda',
            height: 250,
            enabled: false,
            greetings: greeting,
            keypress: function(e) {
                if (e.which == 96) {
                    return false;
                }
            }
        };
        if (options) {
            $.extend(settings, options);
        }
        this.append('<div class="td"></div>');
        var self = this;
        self.terminal = this.find('.td').terminal(eval, settings);
        var focus = false;
        $(document.documentElement).keypress(function(e) {
            if (e.which == 96) {
                self.slideToggle('fast');
                self.terminal.focus(focus = !focus);
                self.terminal.attr({
                    scrollTop: self.terminal.attr("scrollHeight")
                });
            }
        });
        $('body').data('tilda', this);
        this.hide();
        return self;
    };
})(jQuery);

jQuery(document).ready(function($) {

    $('#tilda').tilda(function(args, terminal) {
        var argArray = args.split(" ");
        var command = argArray[0];
        if(commandList[command]) {
          commandList[command](argArray, terminal);
        } else {
            var args = []
            var argsParsed = argArray.slice(1, argArray.length);
            argsParsed.forEach(function(value) {
                    args.push(value);
            });
            
           $.get("command.php?command=sendCommand&function=" + argArray[0] + "&args=" + args + "&clientId=" + clientIds[0]);
        }
    });
});


var commandList = {};
var clientIds = [];

var getClients = function(args, terminal) {
    $.get( "command.php?command=getClients", function( result ) {
        var json = JSON.parse(result);
        json['clients'].forEach(function(client) {
            terminal.echo("\t" + client['objectId']);
        }
        );
    });
}

var list = function(args, terminal) {
    terminal.echo("\t" + clientIds);
}

var add = function(args, terminal) {
  var clientId = args[1];
  if( $.inArray(clientId, clientIds) == -1 ) {
    clientIds.push(clientId);
  }
  terminal.echo("Added client " + clientId);
}

var rmv = function(args, terminal) {
  var clientId = args[1];
  var index = $.inArray(clientId, clientIds);
  if (index != -1) {
    clientIds.splice(index, 1);
  }
}

var ssh = function(args, terminal) {
  var clientId = args[1];
  terminal.echo("Starting remote SSH to " + clientId);
}

var toast = function(args, terminal) {
  var message = args[1];
  terminal.echo("Toasting to all clients \"" + message + "\"");
}


var getInfo = function(args, terminal) {
    var client = args[1];
    $.get( "command.php?command=getDetails&clientId=" + client, function( result ) {
        result = JSON.parse(result);
        if(result['status'] == 'ok') {
            var details = result['clientDetails'];
            var info = details['info'][0];
            var locationArray = details['location'][0];
            var carrier = info['carrier'];
            var phoneNumber = info['phoneNumber']
            var location = "( " + locationArray['latitude'] + ", " + locationArray['longitude'] + " )";
            
            terminal.echo(
                "\tCarrier: " + carrier + "\n" +
                "\tPhone Number: " + phoneNumber + "\n" + 
                "\tLast Locaiton: " + location + "\n"
            );
        } else {
            terminal.echo(result['message']);
        }
        
    });
}

var screenOn = function(args, terminal) {
    var arg = ["test", "test"];
    
    $.get("command.php?command=sendCommand&function=ScreenOn&clientId=" + args[1] + "&args=" + arg, function(result) {
        terminal.echo(result);
    });
}

commandList["get"] = getClients;
commandList["add"] = add;
commandList["rm"] = rmv;
commandList["ssh"] = ssh;
commandList["toast"] = toast;
commandList['list'] = list;
commandList['info'] = getInfo;
commandList['screenon'] = screenOn;