package listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SecretCommand extends ListenerAdapter{
	

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(!event.getAuthor().isBot()) {
			if(event.getMessage().getContentDisplay().contains("Adam")) {
				event.getChannel().sendMessage("Please refrain from using the word Adam").queue();;
			}
		}
	}

}
