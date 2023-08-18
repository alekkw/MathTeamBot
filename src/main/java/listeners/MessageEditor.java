package listeners;

import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageEditor extends ListenerAdapter{
	
	@Override
	public void onMessageUpdate(MessageUpdateEvent e) {
		if(!e.getAuthor().isBot()) {
			e.getMessage().delete().queue();
		}
	}

}
