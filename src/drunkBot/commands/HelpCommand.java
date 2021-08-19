package drunkBot.commands;

import drunkBot.core.DrunkBot;
import drunkBot.handlers.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Map;

public class HelpCommand extends Command {
    @Override
    public String description() {
        return "Take a fuckin guess mate";
    }

    @Override
    public void runCommand(MessageReceivedEvent event) {
        event.getChannel().sendMessage(writeEmbed(event).build()).queue();
    }

    private EmbedBuilder writeEmbed(MessageReceivedEvent event) {


        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Command List");
        eb.setThumbnail(DrunkBot.getJDA().getSelfUser().getAvatarUrl());
        eb.setColor(Color.red);

        //add all commands and descriptions to the embedBuilder
        for(Map.Entry<String, Command> commandSet : new CommandHandler().getCommands().entrySet()){
            eb.addField(("!" + commandSet.getKey()), commandSet.getValue().description(), false);
        }

        return eb;
    }


}
