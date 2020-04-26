package drunkBot.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class NewMemberCommand extends Command{


    @Override
    public String description() {
        return "gonna make you a little account so you can actually use me. " +
                "Just like how i used you're mum last night ;) ";
    }

    @Override
    public void run(MessageReceivedEvent event) {
        //TODO
        //create a db of users and associated data (credits for pokies, etc..)
        //return a message depending on if they are successfully added
        //  or if they are not able to be added for some reason (already added, etc..)
    }
}
