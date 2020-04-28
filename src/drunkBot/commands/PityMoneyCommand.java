package drunkBot.commands;

import drunkBot.core.DrunkBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PityMoneyCommand extends Command{
    @Override
    public String description() {
        return null;
    }

    @Override
    public void runCommand(MessageReceivedEvent event) {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        if(!DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()).getLastPityMoneyUse().equals(date)){
            event.getChannel().sendMessage("Yeah here's ya pity money poor fella").queue();
            DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()).addCredits(20);
            DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()).setLastPityMoneyUse(date);
        }
        else
            event.getChannel().sendMessage("I already gave you pity money, fuck off til tomorrow").queue();
    }
}
