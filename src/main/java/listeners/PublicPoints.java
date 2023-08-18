package listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import sheets.GoogleSheetPoints;

public class PublicPoints extends ListenerAdapter{
	
	private GoogleSheetPoints sheet;
	
	public PublicPoints(GoogleSheetPoints pointsSheet) {
		this.sheet = pointsSheet;
	}
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		
		String eventNature = event.getName();
		
		//Displaying User Points
		if(eventNature.equals("points")) {
			event.deferReply().queue();
			User user = event.getUser();
			int points = sheet.getPoints(user);
			if(points != -1) {
				event.getHook().sendMessage(sheet.getName(user) + " has " + points + " points").queue();
			}else {
				event.getHook().sendMessage("User was not indentified").queue();
			}
			
		}
		
	}

}
