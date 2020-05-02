package drunkBot.core;

import drunkBot.eventListeners.*;
import drunkBot.handlers.JukeboxMessageHandler;
import drunkBot.memberFunctions.MemberFunctions;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import javax.security.auth.login.LoginException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class DrunkBot {

    private static JDA jda;
    private static MemberFunctions memberFunctions;
    private static PropertiesReader properties;

    public static void main(String[] args){
        DrunkBot drunkBot = new DrunkBot();
        drunkBot.run();
    }

    private void run(){

        properties = new PropertiesReader();

        jda = null;
        try {
            jda = JDABuilder.createDefault(properties.getToken()).build();
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

        //Timer to save all data
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                memberFunctions.saveUsers();
                System.out.println("ran");
            }
        }, 3000000, 6000000);

    }

    public static JDA getJDA(){
        return jda;
    }

    public static MemberFunctions getMemberFunctions(){
        return memberFunctions;
    }

    public static PropertiesReader getPropertiesReader(){
        return properties;
    }
}
