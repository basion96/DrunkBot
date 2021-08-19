package drunkBot.commands;

import drunkBot.core.DrunkBot;
import drunkBot.serverFunctions.Member;
import drunkBot.serverFunctions.PubLeaderBoard;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PubLeaderBoardCommand extends Command{

    @Override
    public String description() {
        return "The pubs leaderboard. Top 5 in each category.";
    }

    @Override
    public void runCommand(MessageReceivedEvent event) {
        event.getChannel().sendMessage(getListEmbed().build()).queue();
    }

    private EmbedBuilder getListEmbed(){
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Leaderboards");
        eb.setColor(Color.red);

        eb.addField("Pokie Wins", "Most amount of wins on the pokies", true);
        eb.addField("Pokie Losses", "Most amount of losses on the pokies", true);
        eb.addField("Most Money won", "Most money won on the pokies", true);

        List<Member> pokieWinsList = getPokieWinsList();
        List<Member> pokieLossesList = getPokieLossesList();
        List<Member> mostMostWonList = getMostMoneyWonList();

        for(int i=0; i<5; i++){
            if(pokieWinsList.size()==5 || (pokieWinsList.size()<5 && i<pokieWinsList.size()))
                eb.addField((i+1) + ". " + pokieWinsList.get(i).getName(), Integer.toString(pokieWinsList.get(i).getWins()), true);

            if(pokieLossesList.size()==5 || (pokieLossesList.size()<5 && i<pokieLossesList.size()))
                eb.addField((i+1) + ". " + pokieLossesList.get(i).getName(), Integer.toString(pokieLossesList.get(i).getBiggestWin()), true);

            if(mostMostWonList.size()==5 || (mostMostWonList.size()<5 && i<mostMostWonList.size()))
                eb.addField((i+1) + ". " + mostMostWonList.get(i).getName(), Integer.toString(mostMostWonList.get(i).getWins()), true);
        }
        eb.addField("", "", false);

        eb.addField("Most Money Lost", "Most amount of money lost on the pokies", true);
        eb.addField("Most Dole Money Claimes", "Most amount of dole money claims made", true);
        eb.addField("Highest Rank", "Highest rank, biggest dick", true);

        List<Member> moneyLostList = getMostMoneyLostList();
        List<Member> DoleMoneyList = getDoleMoneyClaimedList();
        List<Member> rankList = getRankList();

        for(int i=0; i<5; i++){
            if(moneyLostList.size()==5 || (moneyLostList.size()<5 && i<moneyLostList.size()))
                eb.addField((i+1) + ". " + moneyLostList.get(i).getName(), Integer.toString(moneyLostList.get(i).getWins()), true);

            if(DoleMoneyList.size()==5 || (DoleMoneyList.size()<5 && i<DoleMoneyList.size()))
                eb.addField((i+1) + ". " + DoleMoneyList.get(i).getName(), Integer.toString(DoleMoneyList.get(i).getBiggestWin()), true);

            if(rankList.size()==5 || (rankList.size()<5 && i<rankList.size()))
                eb.addField((i+1) + ". " + rankList.get(i).getName(), Integer.toString(rankList.get(i).getWins()), true);
        }


        return eb;
    }

    private List<Member> getPokieWinsList(){
        ArrayList<Member> list = (ArrayList<Member>) DrunkBot.getMemberFunctions().getMembers().clone();

        Collections.sort(list, (o1, o2) -> {
            int val1 = o1.getWins();
            int val2 = o2.getWins();
            int x;
            if(val1 > val2)
                x = -1;
            else if(val1 < val2)
                x = 1;
            else
                x = 0;

            return x;
        });

        if(list.size()<5)
            return list;
        else
            return list.subList(0, 5);
    }

    private List<Member> getPokieLossesList(){
        ArrayList<Member> list = (ArrayList<Member>) DrunkBot.getMemberFunctions().getMembers().clone();

        Collections.sort(list, (o1, o2) -> {
            int val1 = o1.getLosses();
            int val2 = o2.getLosses();
            int x;
            if(val1 > val2)
                x = -1;
            else if(val1 < val2)
                x = 1;
            else
                x = 0;

            return x;
        });

        if(list.size()<5)
            return list;
        else
            return list.subList(0, 5);
    }

    private List<Member> getMostMoneyWonList(){
        ArrayList<Member> list = (ArrayList<Member>) DrunkBot.getMemberFunctions().getMembers().clone();

        Collections.sort(list, (o1, o2) -> {
            int val1 = o1.getBiggestWin();
            int val2 = o2.getBiggestWin();
            int x;
            if(val1 > val2)
                x = -1;
            else if(val1 < val2)
                x = 1;
            else
                x = 0;

            return x;
        });

        if(list.size()<5)
            return list;
        else
            return list.subList(0, 5);
    }

    private List<Member> getMostMoneyLostList(){
        ArrayList<Member> list = (ArrayList<Member>) DrunkBot.getMemberFunctions().getMembers().clone();

        Collections.sort(list, (o1, o2) -> {
            int val1 = o1.getTotalLosses();
            int val2 = o2.getTotalLosses();
            int x;
            if(val1 > val2)
                x = -1;
            else if(val1 < val2)
                x = 1;
            else
                x = 0;

            return x;
        });

        if(list.size()<5)
            return list;
        else
            return list.subList(0, 5);
    }

    private List<Member> getDoleMoneyClaimedList(){
        ArrayList<Member> list = (ArrayList<Member>) DrunkBot.getMemberFunctions().getMembers().clone();

        Collections.sort(list, (o1, o2) -> {
            int val1 = o1.getDoleUses();
            int val2 = o2.getDoleUses();
            int x;
            if(val1 > val2)
                x = -1;
            else if(val1 < val2)
                x = 1;
            else
                x = 0;

            return x;
        });

        if(list.size()<5)
            return list;
        else
            return list.subList(0, 5);
    }

    private List<Member> getRankList(){
        ArrayList<Member> list = (ArrayList<Member>) DrunkBot.getMemberFunctions().getMembers().clone();

        Collections.sort(list, (o1, o2) -> {
            int val1 = o1.getPubRank();
            int val2 = o2.getPubRank();
            int x;
            if(val1 > val2)
                x = -1;
            else if(val1 < val2)
                x = 1;
            else
                x = 0;

            return x;
        });

        if(list.size()<5)
            return list;
        else
            return list.subList(0, 5);
    }
}
