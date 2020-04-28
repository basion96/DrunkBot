package drunkBot.core;

import drunkBot.eventListeners.MessageReceivedListener;
import drunkBot.handlers.JukeboxMessageHandler;
import drunkBot.memberFunctions.MemberFunctions;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import javax.security.auth.login.LoginException;
import java.util.Timer;
import java.util.TimerTask;

public class DrunkBot {

    private static JDA jda;
    private static MemberFunctions memberFunctions;
    private Timer timer = new Timer();

    public static void main(String[] args){
        DrunkBot drunkBot = new DrunkBot();
        drunkBot.run();
    }

    private void run(){
        jda = null;
        try {
            jda = JDABuilder.createDefault("").build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        memberFunctions = new MemberFunctions();

        jda.addEventListener(new JukeboxMessageHandler(), new MessageReceivedListener());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                memberFunctions.saveUsers();
            }
        }, 3600000, 3600000);

    }

    public static JDA getJDA(){
        return jda;
    }

    public static MemberFunctions getMemberFunctions(){
        return memberFunctions;
    }
}
