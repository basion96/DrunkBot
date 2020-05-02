package drunkBot.commands;

import drunkBot.core.DrunkBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PokiesCommand extends Command {

    private String[] row1, row2, row3;
    private final String[] symbols = {":eye: ", ":game_die: ", ":ring: ", ":red_car: ", ":snake: "};
    private String jackpotSymbol, majorSymbol, minorSymbol;
    private Boolean wonJackpot, wonMajor, wonMinor;
    private int jackpot, major, minor;

    public PokiesCommand(){
        wonJackpot = false;
        wonMajor = false;
        wonMinor = false;

        jackpot = 0;
        loadData();
        if(jackpot == 0){
            System.out.println("failed to load data");
            jackpot = 1000;
            major = 500;
            minor = 250;
            jackpotSymbol = "<:alex6:703972081573822474>";
            majorSymbol = "<:VB:695221487120023562>";
            minorSymbol = "<:New:695220960474693672>";
        }
        Timer pokiesSaver = new Timer();
        pokiesSaver.schedule(new TimerTask() {
            @Override
            public void run() {
                saveData();
            }
        }, 300000, 600000);
    }

    @Override
    public String description() {
        return "ayye pokies time. Have a game and earn (or lose) some credits!";
    }

    @Override
    public void runCommand(MessageReceivedEvent event){


        String msg = event.getMessage().getContentDisplay();

        //checks to make sure they have given correct arguments
        if(!msg.matches("!pokies (1|5|(10)|(20)|(50))")) {
            if(msg.matches("!pokies info")){
                event.getChannel().sendMessage(getPokiesInfo().build()).queue();
                return;
            }
            event.getChannel().sendMessage("mate, its !pokies [amount] where amount can only be 1, 5, 10, 20").queue();
            return;
        }

        //checks to make sure they are a member
        if(!DrunkBot.getMemberFunctions().isMember(event.getAuthor().getName())){
            event.getChannel().sendMessage("Sorry mate, need to be a member to use this.").queue();
            return;
        }

        //converts the amount they want to bet to an int
        int betAmount=0;
        try{
            betAmount = Integer.parseInt(msg.substring(msg.lastIndexOf(" ")+1));
        } catch (Exception e){
            event.getChannel().sendMessage("mate, its !pokies [amount] where amount can only be 1, 5, 10, 20").queue();
            return;
        }

        //checks to make sure they have enough credit balance
        if(DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()).getBalance() < betAmount){
            event.getChannel().sendMessage("Bro, you dont even have that much.").queue();
            return;
        }

        //the actual stuff

        //generate each rows symbols
        generatePokies();
        int winnings = 0;

        //determine wins
        String winType = "";
        //3 in a row (any row)
        if(row1[0].equals(row1[1]) && row1[1].equals(row1[2])){
            winnings += detemineWinAmount(betAmount, row1[0]);
            winType = "3 in a row!";
        }
        if(row2[0].equals(row2[1]) && row2[1].equals(row2[2])){
            winnings += detemineWinAmount(betAmount, row2[0]);
            winType = "3 in a row!";
        }
        if(row3[0].equals(row3[1]) && row3[1].equals(row3[2])){
            winnings += detemineWinAmount(betAmount, row3[0]);
            winType = "3 in a row!";
        }

        //diagonal square
        if(row1[1].equals(row2[0]) && row2[0].equals(row2[2]) && row3[1].equals(row1[1])) {
            winnings += detemineWinAmount(betAmount, row1[1]);
            winType = "Diagonal 4!";
        }

        //straight diagonal
        if((row1[0].equals(row2[1]) && row2[1].equals(row3[2])) || (row1[2].equals(row2[1]) && row2[1].equals(row3[0]))){
            winnings += detemineWinAmount(betAmount, row2[1]);
            winType = "Straight diagonal!";
        }

        int total = (winnings > 0)? (winnings + betAmount) : (winnings - betAmount);
        if(wonJackpot){
            total += jackpot;
            jackpot = 1000;
        }
        if(wonMajor){
            total += major;
            major = 500;
        }
        if(wonMinor){
            total += minor;
            minor = 250;
        }

        if(total < 0) {
            DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()).addTotalLosses(total);
            DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()).incrementLosses();
            jackpot += betAmount;
            major += betAmount*0.5;
            minor += betAmount*.25;
        }
        else {
            DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()).addTotalWinnings(total);
            DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()).incrementWins();
        }

        DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()).addCredits(total);

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.CYAN);
        eb.setTitle(("Pokies - " + event.getAuthor().getName()));
        eb.addField("Bet Amount:", Integer.toString(betAmount), true);
        eb.addField("Balance remaining: ", Integer.toString(DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()).getBalance()), true);
        eb.addField("", row1[0] + " | " + row1[1] + " | " + row1[2], false);
        eb.addField("",row2[0] + " | " + row2[1] + " | " + row2[2] , false);
        eb.addField("", row3[0] + " | " + row3[1] + " | " + row3[2], false);
        if(!winType.equals("")){
            if(wonJackpot)
                eb.addField("", "Jackpot won!", true);
            if(wonMajor)
                eb.addField("", "Major won!", true);
            if(wonMinor)
                eb.addField("", "Minor won!", true);
            eb.addField("", winType, true).setColor(Color.GREEN);
        }
        eb.addField("Winnings:", Integer.toString(total), false);

        event.getChannel().sendMessage(eb.build()).queue();
        wonJackpot = false;
        wonMajor = false;
        wonMinor = false;

    }

    private int detemineWinAmount(int betAmount, String symbol){
        if(symbol.equals(jackpotSymbol)){
            wonJackpot = true;
            return betAmount*100;
        }

        if(symbol.equals(majorSymbol)){
            wonMajor = true;
            return betAmount*50;
        }

        if(symbol.equals(minorSymbol)){
            wonMinor = true;
            return betAmount*10;
        }

        return betAmount*5;
    }

    private void generatePokies(){
        row1 = new String[3];
        row2 = new String[3];
        row3 = new String[3];
        for(int i = 0; i<3; i++){

            row1[i] = getSymbol();
            row2[i] = getSymbol();
            row3[i] = getSymbol();
        }
    }

    private String getSymbol(){
        int alexHeadChance = 75, vbChance = 50, newChance = 25;

        if(new Random().nextInt(alexHeadChance)<=1)
            return "<:alex6:703972081573822474>";

        if(new Random().nextInt(vbChance)<=1)
            return "<:VB:695221487120023562>";

        if(new Random().nextInt(newChance)<=1)
            return "<:New:695220960474693672>";

        return symbols[new Random().nextInt(symbols.length)];

    }

    private EmbedBuilder getPokiesInfo(){
        EmbedBuilder eb = new EmbedBuilder();

        eb.setColor(Color.CYAN);
        eb.setTitle("Pokies Info");
        eb.setDescription("Welcome to the pokies. use !pokies [amount] to play. valid amounts are 1, 5, 10, 20 only.");
        eb.addField("Jackpot: " + jackpotSymbol, Integer.toString(jackpot), true);
        eb.addField("Major: " + majorSymbol, Integer.toString(major), true);
        eb.addField("minor: " + minorSymbol, Integer.toString(minor), true);

        return eb;
    }

    public void saveData(){
        Properties prop = getProperties();
        prop.setProperty("currentJackpot", Integer.toString(jackpot));
        prop.setProperty("currentMajor", Integer.toString(major));
        prop.setProperty("curentMinor", Integer.toString(minor));


        try {
            prop.store(new FileOutputStream("resources/pokies.properties"), null);
        } catch (IOException e) {
            System.out.println("Failed saving properties");
        }


        System.out.println("Pokies save data successful");
    }

    private boolean loadData(){
        Properties prop = getProperties();
        if(prop == null)
            return false;

        jackpot = Integer.parseInt(prop.getProperty("currentJackpot"));
        major = Integer.parseInt(prop.getProperty("currentMajor"));
        minor = Integer.parseInt(prop.getProperty("curentMinor"));

        jackpotSymbol = prop.getProperty("jackpotSymbol");
        majorSymbol = prop.getProperty("majorSymbol");
        minorSymbol = prop.getProperty("minorSymbol");
        System.out.println("Pokies load data successful");
        return true;
    }

    private Properties getProperties(){
        Properties properties = new Properties();
        String path = "resources/pokies.properties";
        try {
            properties.load(new FileInputStream(path));
        }catch (IOException e){
            System.out.println("could not find "+path.substring(path.lastIndexOf("/")+1)+"\n" + e);
        }

        return properties;
    }
}
