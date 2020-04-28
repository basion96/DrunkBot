package drunkBot.commands;

import drunkBot.core.DrunkBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Random;

public class PokiesCommand extends Command {


    private Random random;
    private String[] row1, row2, row3;
    private String[] symbols = {":bone: ", ":eye: ", ":ok_hand: ", ":game_die: ", ":ring: ", ":red_car: ", ":snake: "};

    //values to bet: 1, 5, 10, 20
    //snake, car, ring, okhand, eye, bone: x2 of bet, 1:20
    //new: x10, 1:50
    //vb: x50, 1:100
    //alex: x100, 1:200

    public PokiesCommand(){
        generatePokies();
    }

    @Override
    public String description() {
        return "Have a game on the Pokies, can bet any amount of credits each game (up to however many you have). !pokies [amount]";
    }

    @Override
    public void runCommand(MessageReceivedEvent event){


        String msg = event.getMessage().getContentDisplay();

        //checks to make sure they have given correct arguments
        if(!msg.matches("!pokies [0-9]*")) {
            event.getChannel().sendMessage("mate, its !pokies [amount]").queue();
            return;
        }

        if(!DrunkBot.getMemberFunctions().isMember(event.getAuthor().getName())){
            event.getChannel().sendMessage("Sorry mate, need to be a member to use this.").queue();
            return;
        }

        //converts the amount they want to bet to an int
        int betAmount=0;
        try{
            betAmount = Integer.parseInt(msg.substring(msg.lastIndexOf(" ")+1));
        } catch (Exception e){
            event.getChannel().sendMessage("mate, its !pokies [amount]").queue();
            return;
        }

        //checks to make sure they have enough credit balance
        if(DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()).getBalance() < betAmount){
            event.getChannel().sendMessage("Bro, you dont even have that much.").queue();
            return;
        }

        generatePokies();
        int winnings = 0;

        //determine win
        if(row1[0].equals(row1[1]) && row1[1].equals(row1[2]))
            winnings += betAmount*5;
        if(row2[0].equals(row2[1]) && row2[1].equals(row2[2]))
            winnings += betAmount*5;
        if(row3[0].equals(row3[1]) && row3[1].equals(row3[2]))
            winnings += betAmount*5;

        int total = winnings - betAmount;
        DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()).addCredits(total);

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.CYAN);
        eb.setTitle(("Pokies - " + event.getAuthor().getName()));
        eb.addField("Bet Amount:", Integer.toString(betAmount), true);
        eb.addField("Balance remaining: ", Integer.toString(DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()).getBalance()), true);
        eb.addField("", row1[0] + " | " + row1[1] + " | " + row1[2], false);
        eb.addField("",row2[0] + " | " + row2[1] + " | " + row2[2] , false);
        eb.addField("", row3[0] + " | " + row3[1] + " | " + row3[2], false);
        eb.addField("Winnings:", Integer.toString(total), false);

        event.getChannel().sendMessage(eb.build()).queue();
    }

    public void generatePokies(){
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
        if(new Random().nextInt(200)<=1)
            return "<:alex6:703972081573822474>";

        if(new Random().nextInt(100)<=1)
            return "<:VB:695221487120023562>";

        if(new Random().nextInt(100)<=1)
            return "<:New:695220960474693672>";

        return symbols[new Random().nextInt(symbols.length)];

    }


}
