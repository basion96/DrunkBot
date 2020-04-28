package drunkBot.memberFunctions;

import drunkBot.core.DrunkBot;

import java.io.Serializable;

public class Member implements Serializable {
    private String name, lastPityMoneyUse;
    private int credits, wins, losses, totalWinnings, totalLosses;

    public Member(String name, int startingCredits){
        this.name = name;
        credits = startingCredits;
        wins = 0;
        losses = 0;
        totalWinnings = 0;
        totalLosses = 0;
        lastPityMoneyUse = "";
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

    public int checkBalance(){
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

    public void setLastPityMoneyUse(String date){
        lastPityMoneyUse = date;
    }

    public String getLastPityMoneyUse(){
        return lastPityMoneyUse;
    }
}
