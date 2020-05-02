package drunkBot.handlers;

import drunkBot.commands.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;

public class CommandHandler{

    private static HashMap<String, Command> commands;

    public void handleCommand(MessageReceivedEvent event){
        String msg = event.getMessage().getContentDisplay().toLowerCase();
        String command = msg.substring(1, (msg.contains(" ")) ? msg.indexOf(" ") : msg.length());

        if(commands.containsKey(command)){
            commands.get(command).runCommand(event);
        }
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public CommandHandler(){
        commands = new HashMap<>();
        commands.put("addme", new NewMemberCommand());
        commands.put("deleteme", new RemoveMemberCommand());
        commands.put("membership", new ShowMemberCardCommand());
        commands.put("pokies", new PokiesCommand());
        commands.put("givejoshlotsofmonies", new SaveUsersCommand());
        commands.put("pitymoney", new PityMoneyCommand());
        commands.put("kob", new KingOfBeersCommand());
        commands.put("help", new HelpCommand());
    }
}