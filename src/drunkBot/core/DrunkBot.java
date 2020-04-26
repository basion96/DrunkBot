package drunkBot.core;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class DrunkBot {
    private static JDA jda;

    public static void main(String[] args){
        DrunkBot drunkBot = new DrunkBot();
        drunkBot.run();
    }

    private void run(){
        JDABuilder bot = new JDABuilder(AccountType.BOT);
        //get token from json file
        bot.setToken("");
        bot.setAutoReconnect(true);

        try{
            jda = bot.buildBlocking();
        }catch(LoginException | InterruptedException e){
            e.printStackTrace();
        }

        bot.addEventListener(

        );

    }

}

//TODO
/*
king of beers
 */
