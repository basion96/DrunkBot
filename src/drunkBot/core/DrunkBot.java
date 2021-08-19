package drunkBot.core;

import drunkBot.eventListeners.*;
import drunkBot.serverFunctions.Member;
import drunkBot.serverFunctions.MemberFunctions;
import drunkBot.serverFunctions.PubLeaderBoard;
import drunkBot.util.TerminalListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrunkBot {

    private static JDA jda;
    private static MemberFunctions memberFunctions;
    private static PubLeaderBoard pubLeaderBoard;

    public static void main(String[] args){
        DrunkBot drunkBot = new DrunkBot();
        drunkBot.run();
    }

    private void run(){
        PropertiesReader properties = new PropertiesReader();

        jda = null;
        try {
            jda = JDABuilder.createDefault(properties.getToken()).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        jda.getPresence().setActivity(Activity.playing("with dem titties"));

        memberFunctions = new MemberFunctions();
        pubLeaderBoard = new PubLeaderBoard();

        jda.addEventListener(
                new MessageReceivedListener(),
                new GuildMemberJoinListener()
        );

        TerminalListener terminalListener = new TerminalListener();
        new Thread(terminalListener).start();

    }

    public static JDA getJDA(){
        return jda;
    }

    public static MemberFunctions getMemberFunctions(){
        return memberFunctions;
    }

    public static  PubLeaderBoard getPubLeaderBoard(){
        return pubLeaderBoard;
    }
}
