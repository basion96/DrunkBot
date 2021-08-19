package drunkBot.commands;

import drunkBot.core.DrunkBot;
import drunkBot.serverFunctions.Member;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class BuyPubRankCommand extends Command{
    @Override
    public String description() {
        return "The higher the rank the bigger your cock.";
    }

    @Override
    public void runCommand(MessageReceivedEvent event) {

        String msg = event.getMessage().getContentDisplay();

        //check if they are a member
        if(!DrunkBot.getMemberFunctions().isMember(event.getAuthor().getName())){
            event.getChannel().sendMessage("Sorry mate, need to be a member to use this.").queue();
            return;
        }
        else if(msg.equals("!rank buy max")){
            buyRanks(event, true);
        }
        else if(msg.equals("!rank buy")){
            buyRanks(event, false);
        }
        else if(msg.equals("!rank info")){
            event.getChannel().sendMessage("In progress").queue();
        }
        else{
            event.getChannel().sendMessage("Mate do [!rank info] to get info on next rank, [!rank buy] to buy next rank, or [!rank buy max] to buy every level until you run out of money").queue();
        }
    }

    private void buyRanks(MessageReceivedEvent event, boolean max){
        Member member = DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName());

        int levelsPurchased = 0;
        int totalCost = 0;

        if(member.getBalance() < calculateCost(member.getPubRank())){    //they dont have the funds to purchase 1 rank
            event.getChannel().sendMessage("Sorry mate you don't have the money, maybe you should hit the pokies aye").queue();
            return;
        }

        if(max){
            do{
                int cost = calculateCost(member.getPubRank());
                member.addCredits(-cost);
                member.pubRankLevelUp();
                totalCost += cost;
                levelsPurchased += 1;

            }while(member.getBalance() > calculateCost(member.getPubRank()));
        }
        else{
            int cost = calculateCost(member.getPubRank());
            member.addCredits(-cost);
            member.pubRankLevelUp();
            totalCost = cost;
            levelsPurchased = 1;
        }
        DrunkBot.getMemberFunctions().saveUsers(member);
        event.getChannel().sendMessage(getLevelUpDetails(event, levelsPurchased, totalCost).build()).queue();
    }

    /**
     *
     * @param initialLevel the members current Pub rank
     * @return the cost to level up 1 rank
     */
    private int calculateCost(int initialLevel){
        return (int) Math.floor(Math.sqrt((initialLevel+1)*2)*100);
    }

    private EmbedBuilder getLevelUpDetails(MessageReceivedEvent event, int levelsPurchased, int totalCost){

        Member member = DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.red);
        eb.setThumbnail(event.getAuthor().getAvatarUrl());

        eb.addField("Old Rank: ", Integer.toString(member.getPubRank()-levelsPurchased), true);
        eb.addField("New Rank: ", Integer.toString(member.getPubRank()), true);
        eb.addField("Rank increases: ", Integer.toString(levelsPurchased), true);
        eb.addField("Total Cost: ", Integer.toString(totalCost), true);

        return eb;
    }
}
