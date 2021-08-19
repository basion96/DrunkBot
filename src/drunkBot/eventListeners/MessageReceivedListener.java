package drunkBot.eventListeners;

import drunkBot.handlers.CommandHandler;
import drunkBot.handlers.MessageHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceivedListener extends ListenerAdapter {

    private MessageHandler messageHandler = new MessageHandler();
    private CommandHandler commandHandler = new CommandHandler();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()){ //if the message event came from a bot
            return;
        }
        else if(event.getMessage().getContentDisplay().startsWith("!")){
            commandHandler.handleCommand(event);
        }
        else{ //if the message is just a standard message
            messageHandler.handleMessage(event);
        }
    }
}
