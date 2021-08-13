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
        eb.addField("!addme", "Gives you a membership to the pub", false);
        eb.addField("!deleteme", "Membership is revoked, probs because you're a shit cunt", false);
        eb.addField("!membership", "Your valuable membership info", false);
        eb.addField("!pokies", " the fuckin pokies mate, do !pokies [amount] where amount can only be 1, 5, 10, 20 or !pokies info", false);
        eb.addField("!givejoshlotsofmonies", "Because he's a poor shit who lives off the dole", false);
        eb.addField("!pitymoney", "For when you lose all your money to the pokies", false);
        eb.addField("!kob", "I think this starts king of beers? i forgot how this works", false);
        eb.addField("!help", "Take a fuckin guess mate", false);

        return eb;
    }


}
