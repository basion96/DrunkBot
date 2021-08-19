package drunkBot.serverFunctions;

import java.io.Serializable;
public class Member implements Serializable {
    private String name, lastPityMoneyUse;
    private int credits, wins, losses, totalWinnings, totalLosses, biggestWin, doleUses, pubRank;

    public Member(String name, int startingCredits){
        this.name = name;
        credits = startingCredits;
        wins = 0;
        losses = 0;
        totalWinnings = 0;
        totalLosses = 0;
        biggestWin = 0;
        lastPityMoneyUse = "";
        doleUses = 0;
        pubRank = 0;
    }

    public void pubRankLevelUp(){
        pubRank++;
    }

    public int getPubRank(){
        return pubRank;
    }

    public void increaseDoleUses(){
        doleUses++;
    }

    public int getDoleUses(){
        return doleUses;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void addCredits(int amount){
        credits += amount;
    }

    public void removeCredits(int amount){
        credits -= amount;
    }

    public int getBalance(){
        return credits;
    }

    public void incrementWins(){
        wins++;
    }

    public int getWins(){
        return wins;
    }

    public void incrementLosses(){
        losses++;
    }

    public int getLosses(){
        return losses;
    }

    public void addTotalWinnings(int amount){
        totalWinnings += amount;
    }

    public int getTotalWinnings(){
        return totalWinnings;
    }

    public void addTotalLosses(int amount){
        totalLosses +=amount;
    }

    public int getTotalLosses(){
        return totalLosses;
    }

    public void checkForBiggestWin(int wonAmount){
        if(wonAmount > biggestWin)
            biggestWin = wonAmount;
    }

    public int getBiggestWin(){
        return biggestWin;
    }

    public void setLastPityMoneyUse(String date){
        lastPityMoneyUse = date;
    }

    public String getLastPityMoneyUse(){
        return lastPityMoneyUse;
    }
}
