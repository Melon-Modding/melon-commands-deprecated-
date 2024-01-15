package prophetsama.testing;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.item.Item;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.Commands;
import net.minecraft.core.net.command.commands.SpawnCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import prophetsama.testing.commands.Spawn;
import prophetsama.testing.commands.StarterKit;
import prophetsama.testing.commands.WhereAmI;
import turniplabs.halplibe.helper.CommandHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;


public class MelonBTACommands implements ModInitializer, GameStartEntrypoint{
    public static final String MOD_ID = "melonbtacommands";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static int[] starterKitIDs;

	//

	public static TomlConfigHandler config;

	public void updateConfig() {

		Toml properties = new Toml("MelonBTA Commands Configuration");

		properties.addEntry("starterKitItems", "List of Item IDs to be added to the Starter Kit." +
															" Must be a string and comma separated.", String.format("%d,%d,%d",Item.toolCompass.id,Item.toolCalendar.id,Item.toolClock.id));
		properties.addEntry("starterKitCooldown", "Length of Time between each use of StarterKit in Seconds", "86400");

		config = new TomlConfigHandler(null, MOD_ID, properties);

		String ItemIDs = config.getString("starterKitItems");
		String[] temp = ItemIDs.split(",");

		starterKitIDs = new int[temp.length];

		for (int i = 0; i < temp.length; i++) {
			starterKitIDs[i] = Integer.parseInt(temp[i]);
		}
	}

	public int[] getStarterKitIDs() { return starterKitIDs.clone(); }


	@Override
	public void onInitialize() {
		updateConfig();
		LOGGER.info("Testing Grounds initialized.");
	}

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {
		for(Command command : Commands.commands){
			if(command instanceof SpawnCommand){
				Commands.commands.remove(command);
				break;
			}
		}
		//CommandHelper.createCommand(new Compass());
		CommandHelper.createCommand(new Spawn());
		CommandHelper.createCommand(new StarterKit());
		CommandHelper.createCommand(new WhereAmI());
	}
}
