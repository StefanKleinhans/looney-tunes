package looney.tunes.listeners;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

public class EventListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        User user = event.getUser();
        String emoji = event.getReaction().getEmoji().getAsReactionCode();
        Channel channel = event.getChannel();
        String channelMention = event.getChannel().getAsMention();
        String jumpLink = event.getJumpUrl();

        String message = user.getAsTag() + " reacted to a message with " + emoji + " in the " + channelMention + " channel!";
        event.getGuild().getDefaultChannel().asTextChannel().sendMessage(message).queue();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        Guild guild = event.getGuild();
        VoiceChannel channel = guild.getVoiceChannelsByName("Music", true).get(0);
        AudioManager manager = guild.getAudioManager();

        if (message.contains("ping")) {
            event.getChannel().sendMessage("pong").queue();
        } else if (message.contains("+play")) {
            manager.openAudioConnection(channel);
        } else if (message.contains("+leave")) {
            manager.closeAudioConnection();
        }
    }
}
