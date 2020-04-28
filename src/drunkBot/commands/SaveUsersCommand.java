package drunkBot.commands;

import drunkBot.core.DrunkBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SaveUsersCommand extends Command{
    @Override
    public String description() {
        return null;
    }

    @Override
    public void runCommand(MessageReceivedEvent event) {
        DrunkBot.getMemberFunctions().saveUsers();
    }
}
