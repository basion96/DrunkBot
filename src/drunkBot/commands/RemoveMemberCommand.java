package drunkBot.commands;

import drunkBot.core.DrunkBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RemoveMemberCommand extends Command{


    @Override
    public String description() {
        return "Dont't wanna be a member anymore? lose access to benefits?";
    }

    @Override
    public void runCommand(MessageReceivedEvent event) {
        String msg = event.getMessage().getContentDisplay();

        if(msg.contains(" ")){
            if(msg.substring(msg.indexOf(" ")+1).equalsIgnoreCase(event.getAuthor().getName())){
                boolean result = DrunkBot.getMemberFunctions().removeMember(event.getAuthor().getName());
                if(result){
                    event.getChannel().sendMessage("If ya say so..").queue();
                    DrunkBot.getMemberFunctions().removeMember(event.getAuthor().getName());
                }
                else{
                    event.getChannel().sendMessage("Bro you're not even a member").queue();
                }
            }
            else{
                event.getChannel().sendMessage("Make sure your name is spelt correctly (!deleteme " + event.getAuthor().getName() + ")").queue();
            }
        }
        else{
            event.getChannel().sendMessage("Gonna need you to confirm by adding your name at the end (!deleteme " + event.getAuthor().getName() + ")").queue();
        }
    }
}
