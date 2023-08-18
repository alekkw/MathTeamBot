package listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import sheets.PointsSheet;

public class PointsListener extends ListenerAdapter{
	
	private PointsSheet pointsSheet;
	
	public PointsListener(PointsSheet sheet) {
		this.pointsSheet = sheet;
	}

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		Message.suppressContentIntentWarning();
		if(event.getName().equals("points")) {
			//event.reply(event.getUser() + " want to see their points").queue();
			event.reply(event.getUser() + " has " + pointsSheet.getPointsByDiscordId(event.getUser().toString()) + " points").queue();
		}
		
	}
	
	

}
