package listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import sheets.PointsSheet;

public class AddUser extends ListenerAdapter{
	
	private PointsSheet pointsSheet;
	
	public AddUser(PointsSheet sheet) {
		this.pointsSheet = sheet;
	}
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		Message.suppressContentIntentWarning();
		if(event.getName().equals("addmember")) {
			
			event.deferReply().queue();
			
			String name = event.getOption("membername").getAsString();
			User memberID = event.getOption("id").getAsUser();
			String division = event.getOption("division").getAsString();
			
			if(name != null && (memberID != null) && division != null) {
				pointsSheet.addUser(name, memberID.toString(), division, 0);
				event.getHook().sendMessage("Member was added: Name: " + name + " ID: " + memberID + " Division: " + division).queue();
				return;
			}
			event.getHook().sendMessage("Something went wrong! Name: " + name + " ID: " + memberID + " Division: " + division).queue();
			
		}
		
	}

}
