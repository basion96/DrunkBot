package drunkBot.handlers;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import static java.util.concurrent.TimeUnit.SECONDS;

public class JukeboxMessageHandler extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getChannel().getId().equals("703956665019662447") && !event.getAuthor().isBot()){
            if(!event.getMessage().getContentDisplay().startsWith("!")){
                event.getMessage().delete().complete();
                event.getChannel().sendMessage("Oi <@" + event.getAuthor().getId() + ">, wrong channel, ya fuckin' muppet").delay(10, SECONDS).flatMap(Message::delete).queue();
            }
        }
    }
}
