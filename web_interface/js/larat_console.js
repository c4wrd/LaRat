var greeting = "Welcome to the LaRat Interactive Console! \n\
                  Commands:\n\
                      list - lists availiable clients\n\
                      add [clientId]  - adds client to current client array\n\
                      rm [clientId]   - removes client from client pool\n\
                      ssh [clientId]  - creates remote shell on client (client pool not supported)\n\
                      toast [message] - toasts message on clients in current client array\n\
                      list            - lists clients in current client array";

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
          terminal.echo("Invalid command " + command);
        }
    });
});


var commandList = {};
var clientIds = [];

var list = function(args, terminal) {
  terminal.echo("SHIM - list called");
}

var add = function(args, terminal) {
  var clientId = args[1];
  if( $.inArray(clientId, clientIds) == -1 ) {
    clientIds.push(clientId);
  }
  terminal.echo("Add client " + clientId);
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
  terminal.echo("SSH to " + clientId);
}

var toast = function(args, terminal) {
  var message = args[1];
  terminal.echo("Toasting to all clients \"" + message + "\"");
}


commandList["list"] = list;
commandList["add"] = add;
commandList["rm"] = rmv;
commandList["ssh"] = ssh;
commandList["toast"] = toast;
