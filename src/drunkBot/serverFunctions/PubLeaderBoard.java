package drunkBot.serverFunctions;

import drunkBot.core.DrunkBot;

import java.util.*;

public class PubLeaderBoard {
    private Map<String, Member> customLeaderboard;

    public List<Member> getRankList(String type){
        List<Member> biggestPokieWins = DrunkBot.getMemberFunctions().getMembers();

        Collections.sort(biggestPokieWins, (o1, o2) -> {
            int val1=0, val2=0;

            switch (type){
                case "pokiewins": val1 = o1.getWins(); val2 = o2.getWins();
                    break;
                case "money": val1 = o1.getBalance(); val2 = o2.getBalance();
                    break;
                case "pokielose": val1 = o1.getLosses(); val2 = o2.getLosses();
                    break;
                case "biggestwin": val1 = o1.getBiggestWin(); val2 = o2.getBiggestWin();
                    break;
                case "doleuse": val1 = o1.getDoleUses(); val2 = o2.getDoleUses();
                    break;
                case "rank": val1 = o1.getPubRank(); val2 = o2.getPubRank();
            }

            int x;
            if(val1 > val2)
                x = -1;
            else if(val1 < val2)
                x = 1;
            else
                x = 0;

            return x;
        });

        biggestPokieWins = biggestPokieWins.subList(0, 5);

        return biggestPokieWins;
    }
}
