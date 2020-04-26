package drunkBot.handlers;

import drunkBot.commands.Command;
import drunkBot.commands.NewMemberCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.HashMap;

public final class CommandHandler {

    private static HashMap<String, Command> commands;

    public void handleCommand(MessageReceivedEvent event){
        String msg = event.getMessage().getContentDisplay().toLowerCase();
        String command = msg.substring(1, (msg.contains(" ")) ? msg.indexOf(" ") : msg.length());

        if(commands.containsKey(command)){
            commands.get(command).run(event);
        }
        else{
            event.getChannel().sendMessage("Yeah nah can't find that command, need me to call a taxi?").queue();
        }
    }

    public static HashMap<String, Command> getCommands() {
        return commands;
    }

    public CommandHandler(){
        commands = new HashMap<>();
        commands.put("addme", new NewMemberCommand());
    }
}
