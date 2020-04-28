package drunkBot.commands;

import drunkBot.core.DrunkBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ShowMemberCardCommand extends Command{
    @Override
    public String description() {
        return "Displays your member card if you are a member";
    }

    @Override
    public void runCommand(MessageReceivedEvent event) {
        if(DrunkBot.getMemberFunctions().isMember(event.getAuthor().getName()))
            event.getChannel().sendMessage(DrunkBot.getMemberFunctions().generateEmbededMessage(event.getAuthor()).build()).queue();
        else
            event.getChannel().sendMessage("Sorry mate, need to be a member to see that").queue();
    }
}
