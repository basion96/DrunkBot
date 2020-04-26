package drunkBot.commands;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class Command {

    /**
     *
     * @return string containing a description of the what the command does
     */
    public abstract String description();

    public abstract void run(MessageReceivedEvent event);

    protected boolean checkRole(String role, Member member){
        for(Role memberRole : member.getRoles()){
            if(memberRole.getName().equals(role)){
                return true;
            }
        }
        return false;
    }

}
