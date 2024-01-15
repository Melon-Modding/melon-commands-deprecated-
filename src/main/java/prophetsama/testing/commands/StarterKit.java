package prophetsama.testing.commands;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import prophetsama.testing.MelonBTACommands;

import java.time.Duration;
import java.util.HashMap;

public class StarterKit extends Command {
	private final static String COMMAND = "starterkit";
	private final static String NAME = "Starter Kit";

public static String hmsConversion(long millis) {

	Duration duration = Duration.ofMillis(millis);

	long h = duration.toHours();
	long m = duration.toMinutes() % 60;
	long s = duration.getSeconds() % 60;

    return String.format("%02d Hours, %02d Minutes, and %02d Seconds", h, m, s);
}

	public static long starterKitCooldown = MelonBTACommands.config.getInt("starterKitCooldown") * 1000L;
	public StarterKit() {
		super(COMMAND);
	}

	@Override
	public boolean execute(CommandHandler handler, CommandSender sender, String[] strings) {
		if(strings.length > 0 && strings[0].equals("reset")){
			cooldowns.put(sender.getPlayer().username, 0L);
			sender.sendMessage( "StarterKit Cooldown Reset");
			return true;
		}
		if(System.currentTimeMillis() - cooldowns.getOrDefault(sender.getPlayer().username, 0L) > starterKitCooldown){
			cooldowns.put(sender.getPlayer().username, System.currentTimeMillis());
			for (int i : MelonBTACommands.starterKitIDs) {
				sender.getPlayer().inventory.insertItem(new ItemStack(i, 1, 1), false);
			}
			sender.sendMessage( "Given 1x " + NAME + " to " + sender.getPlayer().username);
		}
		else{
			sender.sendMessage("§eYou've already used StarterKit today... time left until next kit: ");
			sender.sendMessage("§e" + hmsConversion(starterKitCooldown - (System.currentTimeMillis() - cooldowns.getOrDefault(sender.getPlayer().username, 0L))));
		}


		return true;
	}

	@Override
	public boolean opRequired(String[] strings) {
		if(strings.length > 0 && strings[0].equals("reset")){
			return true;
		}
		return false;
	}

	@Override
	public void sendCommandSyntax(CommandHandler handler, CommandSender sender) {
		// Feedback to the player that it executed
		sender.sendMessage("/starterkit");
	}

	public static HashMap<String, Long> cooldowns = new HashMap<>();

}
