package drunkBot.util;

import drunkBot.core.DrunkBot;

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
            readCommand(input);
        }
    }

    private void readCommand(String input){
        String[] args = input.split(" ");
        String cmd = args[0];
        switch (cmd){
            // format = !money [user] [amount]
            case "money":
                DrunkBot.getMemberFunctions().getMember(args[1]).addCredits(Integer.parseInt(args[2]));
        }
    }

    public void activateTerminalListener(){
        active = !active;
    }
}
