package drunkBot.commands;

import drunkBot.core.DrunkBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class NewMemberCommand extends Command{

    public String description() {
        return "gonna make you a little account so you can actually use me. " +
                "Just like how i used you're mum last night ;) ";
    }

    public void runCommand(MessageReceivedEvent event){
        boolean result = DrunkBot.getMemberFunctions().addMember(event.getAuthor().getName());
        if(result){
            event.getChannel().sendMessage("<@"+event.getAuthor().getId()+"> yeah mate no problem.").queue();
            event.getChannel().sendMessage(DrunkBot.getMemberFunctions().generateEmbededMessage(event.getAuthor()).build()).queue();
        }
        else{
            event.getChannel().sendMessage("<@" + event.getAuthor().getId() +  "> couldn't add ya mate. Have you already been added?").queue();
        }
    }
}
