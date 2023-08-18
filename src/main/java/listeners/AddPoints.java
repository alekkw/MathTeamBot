package listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import sheets.GoogleSheetPoints;
import sheets.PointsSheet;

public class AddPoints extends ListenerAdapter{
	
	private GoogleSheetPoints pointsSheet;
	
	public AddPoints(GoogleSheetPoints sheet) {
		this.pointsSheet = sheet;
	}
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		Message.suppressContentIntentWarning();
		if(event.getName().equals("addpoints")) {
			
			event.deferReply().queue();
			
			String name = event.getOption("membername").getAsString();
			int addedPoints = event.getOption("points").getAsInt();
			
			if(name != null && addedPoints != 0) {
				if(event.getUser().getName().equals("alekjay")) {
					boolean success = pointsSheet.addPoints(name, addedPoints);
					if(success) {
						event.getHook().sendMessage("Points were added successfully: Name: " + name + " Points: " + addedPoints).queue();
					}else {
						event.getHook().sendMessage("Points were NOT added: Name: " + name + " Points: " + addedPoints).queue();
					}
					
				}else {
					event.getHook().sendMessage("Permission Denied").queue();;
				}
				return;
			}
			event.getHook().sendMessage("Something went wrong! Name: " + name + "Points: " + addedPoints).queue();
			
		}
		
	}

}
