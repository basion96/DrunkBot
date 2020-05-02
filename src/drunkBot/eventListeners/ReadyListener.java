package drunkBot.eventListeners;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter {

    public void onReady(ReadyEvent event){
        JDA jda = event.getJDA();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
    }
}
