package drunkBot.eventListeners;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberJoinListener extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        TextChannel channel = event.getJDA().getTextChannelById("704353773606273045");
        channel.sendMessage("Welcome to the party <@" + event.getMember().getId() + "> . Start slamming some drinks aye :beer: ").queue();
    }
}
