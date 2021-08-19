package drunkBot.commands;

import drunkBot.core.DrunkBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ShowMemberCardCommand extends Command{
    @Override
    public String description() {
        return "It's ya members card bro, show it to the chick at the bar to get sneaky discounts aye";
    }

    @Override
    public void runCommand(MessageReceivedEvent event) {
        if(DrunkBot.getMemberFunctions().isMember(event.getAuthor().getName()))
            event.getChannel().sendMessage(DrunkBot.getMemberFunctions().generateEmbeddedMessage(event.getMember()).build()).queue();
        else
            event.getChannel().sendMessage("Sorry mate, need to be a member to see that").queue();
    }
}
