package main;

import org.jetbrains.annotations.NotNull;

import listeners.AddPoints;
import listeners.AddUser;
import listeners.DivisionList;
import listeners.MessageEditor;
import listeners.PointsListener;
import listeners.PrivatePoints;
import listeners.PublicPoints;
import listeners.SecretCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import sheets.GoogleSheetPoints;
import sheets.PointsSheet;
import tools.Format;
import tools.Format.Category;

public class DiscordBot{
	
	public static void main(String args[]) {
		
		Format format = new Format(Category.NAME, Category.DISCORD_ID, Category.DIVISION, Category.POINTS);
		GoogleSheetPoints points = new GoogleSheetPoints("Points Sheet", "1owHTE9QLYQ6nztkVVXSnMACt35w4IUyQUBnNe0NB3ZI", format);
		
		String[] authorizedUsers = {"alekjay"};
		
		JDA bot = JDABuilder.createDefault("MTA5NzY3ODM0ODMyNDQ1NDUxMQ.G_x8NO.sutycK5Y0Ky2hzV2dXTtzCUE0ej6-eMAdhdgO0")
				.setActivity(Activity.playing("Math"))
				.enableIntents(GatewayIntent.MESSAGE_CONTENT)
				.addEventListeners(new PublicPoints(points), new PrivatePoints(points, authorizedUsers))
				.build();
		
		try {
			bot.awaitReady();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Guild guild = bot.getGuildById("716671358616207381");
		if(guild != null) {
			guild.upsertCommand("points", "Get your current Mu Alpha Theta club points").queue();
			guild.upsertCommand("addmember", "Add a Mu Alpha Theta Member")
								.addOptions(new OptionData[] {new OptionData(OptionType.STRING, "membername", "The first and last name of the member", true),
										    				  new OptionData(OptionType.USER, "id", "The indentification of the User", true),
										    				  new OptionData(OptionType.STRING, "division", "Current Division of Member", true)})
							    .queue();
			guild.upsertCommand("addpoints", "Add points to Mu Alpha Theta Member")
								.addOptions(new OptionData[] {new OptionData(OptionType.STRING, "membername", "The first and last name of the member", true),
															 new OptionData(OptionType.INTEGER, "points", "The amount of points to be added to the member", true)}).queue();
//			guild.upsertCommand("division", "Displays a list of all Names belonging to a certain Division")
//								.addOptions(new OptionData[] {new OptionData(OptionType.STRING, "division", "division", true)})
//								.queue();
		}else {
			System.out.println("AHHHHHHHHHHH");
		}
		
	}
	
	
	

}
