package drunkBot.util;

import drunkBot.core.DrunkBot;
import drunkBot.serverFunctions.Member;

import java.util.Scanner;

public class TerminalListener implements Runnable{

    private boolean active;

    public TerminalListener(){
        this.active = true;
    }

    @Override
    public void run() {
        Scanner keyboard = new Scanner(System.in);
        while(active){
            String input = keyboard.nextLine();
            System.out.println("Terminal: " + input);
            readCommand(input);
        }
    }

    private void readCommand(String input){
        String[] args = input.split(" ");
        String cmd = args[0];
        switch (cmd){
            case "addmoney": // format = !money [user] [amount]
                addMoney(args[1], Integer.parseInt(args[2]));
                break;
            case "removemoney": //// format = !money [user] [amount]
                removeMoney(args[1], Integer.parseInt(args[2]));
        }
    }

    public void activateTerminalListener(){
        active = !active;
    }

    public void addMoney(String username, int amount){
        if(DrunkBot.getMemberFunctions().isMember(username))
            DrunkBot.getMemberFunctions().getMember(username).addCredits(amount);
        else
            System.out.println("User '" + username + "' does not exist.");
    }

    public void removeMoney(String username, int amount){
        if(!DrunkBot.getMemberFunctions().isMember(username)){
            System.out.println("User '" + username + "' does not exist.");
            return;
        }

        Member member = DrunkBot.getMemberFunctions().getMember(username);
        if(member.getBalance()-amount < 0)
            member.removeCredits(member.getBalance());
        else
            member.removeCredits(amount);
    }
}
