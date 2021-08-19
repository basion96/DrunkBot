package drunkBot.commands;

import drunkBot.core.DrunkBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PityMoneyCommand extends Command{

    private int doleAmount = 50;
    @Override
    public String description() {
        return "Run out of money? claim the dole and get " + doleAmount + " credits";
    }

    @Override
    public void runCommand(MessageReceivedEvent event) {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        if(!DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()).getLastPityMoneyUse().equals(date)){
            event.getChannel().sendMessage("Yeah here's ya pity money poor fella").queue();
            DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()).addCredits(doleAmount);
            DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()).setLastPityMoneyUse(date);
            DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()).increaseDoleUses();
            DrunkBot.getMemberFunctions().saveUsers(DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()));

        }
        else
            event.getChannel().sendMessage("I already gave you pity money, fuck off til tomorrow").queue();
    }
}
