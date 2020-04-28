package drunkBot.handlers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MessageHandler extends ListenerAdapter {

    private HashMap<Pattern, String> responses;

    public void handleMessage(MessageReceivedEvent event){
        String msg = event.getMessage().getContentDisplay();

        for(Map.Entry<Pattern, String> entry : responses.entrySet()){
            if(entry.getKey().matcher(msg).find()){
                event.getChannel().sendMessage(entry.getValue()).queue();
                break;
            }
        }
    }

    private void init_responses(){
        responses = new HashMap<>();
        responses.put(
                Pattern.compile("fuck you drunkbot"),
                "Nah mate fuck you, fuckin greasy dog");
    }

    public MessageHandler(){
        init_responses();
    }
}
