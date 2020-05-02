package drunkBot.core;

import drunkBot.eventListeners.*;
import drunkBot.handlers.JukeboxMessageHandler;
import drunkBot.memberFunctions.MemberFunctions;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
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
            jda = JDABuilder.createDefault("NzAzOTYxNzc5MjI2ODA0MjU0.XqWNiQ.we9NP-v4vduF6l6JNkYVN8YSsO4").build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        jda.getPresence().setActivity(Activity.playing("with dem titties"));

        memberFunctions = new MemberFunctions();

        jda.addEventListener(
                new MessageReceivedListener(),
                new JukeboxMessageHandler(),
                new GuildMemberJoinListener(),
                new PokiesMessageHandler()
        );

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                memberFunctions.saveUsers();
            }
        }, 300000, 600000);

    }

    public static JDA getJDA(){
        return jda;
    }

    public static MemberFunctions getMemberFunctions(){
        return memberFunctions;
    }
}
