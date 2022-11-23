package looney.tunes;

import io.github.cdimascio.dotenv.Dotenv;
import looney.tunes.listeners.EventListener;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class LooneyTunes {

    private final Dotenv config;
    private final ShardManager shardManager;

    public LooneyTunes() throws LoginException {
        config = Dotenv.configure().ignoreIfMissing().load();
        String token = config.get("TOKEN");

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT).setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("Pornhub.com"));
        shardManager = builder.build();

        // Register Listeners
        shardManager.addEventListener(new EventListener());
    }

    public static void main(String[] args) {
        try {
            LooneyTunes bot = new LooneyTunes();
        } catch (LoginException e) {
            System.out.println("ERROR: Provided bot token is invalid");
        }
    }
}
