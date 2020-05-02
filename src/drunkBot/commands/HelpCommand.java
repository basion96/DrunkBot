package drunkBot.commands;

import drunkBot.core.DrunkBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.math.BigInteger;

public class HelpCommand extends Command {
    @Override
    public String description() {
        return "This?";
    }

    @Override
    public void runCommand(MessageReceivedEvent event) {
        event.getChannel().sendMessage(writeEmbed(event).build()).queue();
    }

    private EmbedBuilder writeEmbed(MessageReceivedEvent event) {


        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Command List");
        eb.setThumbnail(DrunkBot.getJDA().getSelfUser().getAvatarUrl());


        return eb;
    }


}
