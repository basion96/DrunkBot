package drunkBot.commands;

import drunkBot.core.DrunkBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class NewMemberCommand extends Command{

    public String description() {
        return "Become a valued patron of this fine establishment.";
    }

    public void runCommand(MessageReceivedEvent event){
        boolean memberAdded = DrunkBot.getMemberFunctions().addMember(event.getAuthor().getName());
        if(memberAdded){
            event.getChannel().sendMessage("<@"+event.getAuthor().getId()+"> yeah mate no problem.").queue();
            event.getChannel().sendMessage(DrunkBot.getMemberFunctions().generateEmbeddedMessage(event.getMember()).build()).queue();
            DrunkBot.getMemberFunctions().saveUsers(DrunkBot.getMemberFunctions().getMember(event.getAuthor().getName()));
        }
        else{
            event.getChannel().sendMessage("<@" + event.getAuthor().getId() +  "> couldn't add ya mate. Have you already been added?").queue();
        }
    }
}
