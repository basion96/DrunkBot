package drunkBot.commands;

import drunkBot.core.DrunkBot;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Command {

    /**
     *
     * @return string containing a description of the what the command does
     */
    public abstract String description();

    public abstract void runCommand(MessageReceivedEvent event);

    protected boolean checkRole(String role, Member member){
        for(Role memberRole : member.getRoles()){
            if(memberRole.getName().equals(role)){
                return true;
            }
        }
        return false;
    }
}
